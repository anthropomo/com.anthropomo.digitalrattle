package com.anthropomo.digitalrattle.models;

public class MusicNotation {
	
	private static int major[] = {0, 2, 4, 5, 7, 9, 11};
	private int notes[];
	private int numNotes;

	public MusicNotation(){}
	
	public void setNewMusic(boolean majorKey, int key, int startNote, int numNotes){
		this.numNotes = numNotes;
		setNotes(majorKey, startNote, key);
	}
	
	public int getNote(int num){
		return notes[num];
	}
	
    public void setNotes(boolean mKey, int startNote, int key){
	      if(mKey){
	    	  notes = fillMajor(startNote,numNotes,key);
	      }
	      else{
	    	  notes = fillChrom(startNote,numNotes);
	      }
    }
  
	// fills array of length size with sequential ints beginning with start, returns array of ints.
	private int[] fillChrom(int start, int size){
		int a[] = new int[size];
		for (int i = 0; i < a.length; i++){
			a[i] = start + i;
		}
		return a;
	}
	
	private int[] fillMajor(int start, int size, int key){
		int startNote = start % 12;
		for(int i = 0; i < major.length; i++){
			if(major[i] == startNote){
				startNote = i;
				break;
			}
		}
		if(key>6)
			key -= 12;
		int a[] = new int[size];
		int buffer = -major[startNote];
		for(int i = 0; i < a.length; i++){
			a[i] = key + start + major[startNote] + buffer;
			startNote++;
			if(startNote == major.length){
				buffer = buffer + 12;
				startNote = 0;
			}
		}
		return a;
	}
}
