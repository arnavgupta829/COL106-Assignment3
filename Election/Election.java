package col106.assignment3.Election;
import col106.assignment3.BST.BST;
import col106.assignment3.BST.BSTNode;
import col106.assignment3.Heap.Heap;

import java.util.ArrayList;


public class Election implements ElectionInterface {
	/*
	 * Do not touch the code inside the upcoming block
	 * If anything tempered your marks will be directly cut to zero
	 */
	public static void main() {
		ElectionDriverCode EDC = new ElectionDriverCode();
		System.setOut(EDC.fileout());
	}
	/*
	 * end code
	 */

	//write your code here
	class Tuple{
		public String name;
		public Heap<String, Integer> heap;
		public Tuple(String name, Heap<String, Integer> heap){
			this.name = name;
			this.heap = heap;
		}
	}

	BST<String, Integer> candAndOtherInfo = new BST<String, Integer>();
	BST<String, Integer> candAndVotes = new BST<String, Integer>();
	ArrayList<Tuple> tkic = new ArrayList<>();
	ArrayList<Tuple> lps = new ArrayList<>();
	Heap<String, Integer> glp = new Heap<String, Integer>();
	ArrayList<String[]> candInfo = new ArrayList<>();

	private boolean partyExistsState(String state, String party){
		for(int i = 0; i<lps.size(); i++){
			if(lps.get(i).name.equals(state)){
				if(lps.get(i).heap.keyExists(party))
					return true;
				return false;
			}
		}
		return false;
	}

	private boolean partyExists(String party){
		for(int i = 0; i<candInfo.size(); i++){
			if(candInfo.get(i)[3].equals(party))
				return true;
		}
		return false;
	}

	private String returnParty(String name){
		for(int i = 0; i<candInfo.size(); i++){
			if(candInfo.get(i)[0].equals(name))
				return candInfo.get(i)[3];
		}
		return "";
	}

	private boolean constituencyExists(String constituency){
		for(int i = 0; i<tkic.size(); i++){
			if(tkic.get(i).name.equals(constituency))
				return true;
		}
		return false;
	}

	private Tuple returnConstituency(String constituency){
		for(int i = 0; i<tkic.size(); i++) {
			if (tkic.get(i).name.equals(constituency))
				return tkic.get(i);
		}
		return null;
	}

	private boolean stateExists(String state){
		for(int i = 0; i<lps.size(); i++){
			if(lps.get(i).name.equals(state))
				return true;
		}
		return false;
	}

	private Tuple returnState(String state){
		for(int i = 0; i<lps.size(); i++){
			if(lps.get(i).name.equals(state))
				return lps.get(i);
		}
		return null;
	}

	private String[] returnCandInfo(String finder){
		for(int i = 0; i<candInfo.size(); i++){
			if(candInfo.get(i)[0].equals(finder))
				return candInfo.get(i);
		}
		return null;
	}

	public void insert(String name, String candID, String state, String district, String constituency, String party, String votes){
		//write your code here
		String[] info = {name+", "+candID, state, constituency,  party, district};

		String nameAndID = name+", "+candID;
		int votesCand = Integer.valueOf(votes);
		candAndVotes.insert(nameAndID, votesCand);
		candAndOtherInfo.insert(name+", "+candID+", "+state+", "+district+", "+constituency+", "+party, votesCand);

		if(constituencyExists(constituency)){
			returnConstituency(constituency).heap.insert(nameAndID, votesCand);
		}
		else{
			Heap<String, Integer> tkicInstance = new Heap<String, Integer>();
			tkicInstance.insert(nameAndID, votesCand);
			Tuple tkicTuple = new Tuple(constituency, tkicInstance);
			tkic.add(tkicTuple);
		}

		if(stateExists(state)){
			if(partyExistsState(state, party)) {
				int oldVotes = returnState(state).heap.getValue(party);
				int newVotes = oldVotes + votesCand;
				returnState(state).heap.increaseKey(party, newVotes);
			}
			else{
				returnState(state).heap.insert(party, votesCand);
			}
		}
		else{
			Heap<String, Integer> lpsInstance = new Heap<String, Integer>();
			lpsInstance.insert(party, votesCand);
			Tuple lpsTuple = new Tuple(state, lpsInstance);
			lps.add(lpsTuple);
		}

		if(partyExists(party)){
			int newVotes = votesCand + glp.getValue(party);
			glp.increaseKey(party, newVotes);
		}
		else{
			glp.insert(party, votesCand);
		}
		candInfo.add(info);
	}
	public void updateVote(String name, String candID, String votes){
		//write your code here
		int FinalVotes = Integer.valueOf(votes);
		String finder = name+", "+candID;
		String[] info = returnCandInfo(finder);
		String state = info[1];
		String constituency = info[2];
		String party = info[3];
		String finderInfo = info[0]+", "+info[1]+", "+info[4]+", "+info[2]+", "+info[3];
		int oldVotes = candAndVotes.valueOfKey(finder);
		oldVotes = oldVotes;
		int delVotes = FinalVotes - oldVotes;

		candAndVotes.update(finder, FinalVotes);
		candAndOtherInfo.update(finderInfo, FinalVotes);

		int newVotesGlp = delVotes + glp.getValue(party);
		glp.increaseKey(party, newVotesGlp);

		int newVotesLps = delVotes + returnState(state).heap.getValue(party);
		returnState(state).heap.increaseKey(party, newVotesLps);

		if(!(votes.equals("0")))
			returnConstituency(constituency).heap.increaseKey(finder, FinalVotes);
	}

	public void updateWhileRemove(String finder){
		String info[] = returnCandInfo(finder);
		String state = info[1];
		String constituency = info[2];
		String party = info[3];
		String finderInfo = info[0]+", "+info[1]+", "+info[4]+", "+info[2]+", "+info[3];

		int delVotes = candAndVotes.valueOfKey(finder);

		candAndVotes.delete(finder);
		candAndOtherInfo.delete(finderInfo);

		int newVotesGlp = glp.getValue(party)-delVotes;
		glp.decreaseKey(party, newVotesGlp);

		int newVotesLps = returnState(state).heap.getValue(party)-delVotes;
		returnState(state).heap.decreaseKey(party, newVotesLps);

	}

	public void topkInConstituency(String constituency, String k){
		//write your code here
		Tuple temp = returnConstituency(constituency);
		Heap<String, Integer> temp2 = temp.heap;
		int limit = (temp2.getSize() > Integer.valueOf(k))?(Integer.valueOf(k)):temp2.getSize();
		ArrayList<String> output = new ArrayList<>();
		ArrayList<Integer> outputVotes = new ArrayList<>();
		String str = "";
		str = temp2.peekTopKey()+", "+returnParty(temp2.peekTopKey());
		output.add(str);
		outputVotes.add(temp2.getValue(temp2.peekTopKey()));
		for(int i = 1; i<limit; i++){
			str = temp2.getKeyAtIndex(i)+", "+returnParty(temp2.getKeyAtIndex(i));
			output.add(str);
			outputVotes.add(temp2.getValue(temp2.getKeyAtIndex(i)));
		}
		for(int i = 0; i<outputVotes.size()-1; i++){
			for(int j = i+1; j<outputVotes.size(); j++){
				if(outputVotes.get(i)<outputVotes.get(j)){
					String temp3 = output.get(i);
					output.set(i, output.get(j));
					output.set(j, temp3);
				}
			}
		}
		for(int i = 0; i<output.size(); i++)
			System.out.println(output.get(i));
	}
	public void leadingPartyInState(String state){
		//write your code here
		Heap<String, Integer> leadingParty = returnState(state).heap;
		ArrayList<String> output = new ArrayList<>();
		output.add(leadingParty.peekTopKey());
		int maxVotes = leadingParty.getValue(leadingParty.peekTopKey());
//		while(leadingParty.getSize() != 0){
//			String temp = leadingParty.peekTopKey();
//			int partyVotes = leadingParty.extractMax();
//			if(partyVotes == maxVotes)
//				output.add(leadingParty.peekTopKey());
//		}
		for(int i = 1; i<leadingParty.getSize(); i++){
			String temp = leadingParty.getKeyAtIndex(i);
			int tempVotes = leadingParty.getValue(temp);
			if(tempVotes == maxVotes)
				output.add(temp);
		}
		for(int i = 0; i<output.size()-1; i++) {
			for(int j = i+1; j<output.size(); j++) {
				if(output.get(i).compareTo(output.get(j)) > 0){
					String temp = output.get(i);
					output.set(i, output.get(j));
					output.set(j, temp);
				}
			}
		}
		for(int i = 0; i<output.size(); i++)
			System.out.println(output.get(i));
	}

	private String[] split(String input){
		String out[] = input.split(", ", 2);
		return out;
	}

	public void cancelVoteConstituency(String constituency){
		//write your code here
		Heap<String, Integer> candInConst = returnConstituency(constituency).heap;
		ArrayList<String[]> output = new ArrayList<>();
		for(int i = 0; i<candInConst.getSize(); i++){
			String finder = candInConst.getKeyAtIndex(i);
//			updateWhileRemove(finder, "0");
			output.add(split(finder));
		}
		for(int i = 0; i<output.size()-1; i++){
			for(int j = i+1; j<output.size(); j++){
				if(output.get(i)[1].compareTo(output.get(j)[1]) > 0){
					String[] temp3 = output.get(i);
					output.set(i, output.get(j));
					output.set(j, temp3);
				}
			}
		}
		for(int i = 0; i<output.size(); i++){
			updateWhileRemove(output.get(i)[0]+", "+output.get(i)[1]);
		}
		for(int i = 0; i<tkic.size(); i++){
			if(tkic.get(i).name.equals(constituency))
				tkic.remove(i);
		}
	}

	public void leadingPartyOverall(){
		//write your code here
		ArrayList<String> output = new ArrayList<>();
		output.add(glp.peekTopKey());
		int maxVotes = glp.getValue(glp.peekTopKey());
		for(int i = 1; i<glp.getSize(); i++){
			String temp = glp.getKeyAtIndex(i);
			int tempVotes = glp.getValue(temp);
			if(tempVotes == maxVotes)
				output.add(temp);
		}
		for(int i = 0; i<output.size()-1; i++) {
			for(int j = i+1; j<output.size(); j++) {
				if(output.get(i).compareTo(output.get(j)) > 0){
					String temp = output.get(i);
					output.set(i, output.get(j));
					output.set(j, temp);
				}
			}
		}
		for(int i = 0; i<output.size(); i++)
			System.out.println(output.get(i));
	}
	public void voteShareInState(String party,String state){
		//write your code here
		Heap<String, Integer> stateVotes = returnState(state).heap;
		int totalVotes = 0;
		for(int i = 0; i<stateVotes.getSize(); i++){
			totalVotes += stateVotes.getValue(stateVotes.getKeyAtIndex(i));
		}
		int votesReqd = stateVotes.getValue(party);
		int perc = (votesReqd*100/totalVotes);
		System.out.println(perc);
	}

	public void printElectionLevelOrder() {
		//write your code here
		candAndOtherInfo.printBST();
	}
}