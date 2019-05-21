package com.example.tvsapp.viewModel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvsapp.model.Userdetail;

import java.util.ArrayList;

public class ViewModelTVS extends ViewModel {

	//to display the list of all user details fetched from rest Api
	ArrayList<Userdetail> userList = new ArrayList<>();

	//to display details of the clicked item
	private Userdetail currentUserdetail;

	//to replace fragments
	private MutableLiveData<String> fragmentSwitcherLiveData = new MutableLiveData<>();

	//the index of clicked item
	private int currentPos = 0;

	public Userdetail getCurrentUserdetail() {
		return currentUserdetail;
	}

	public void setCurrentUserdetail(Userdetail currentUserdetail) {
		this.currentUserdetail = currentUserdetail;
	}

	public ArrayList<Userdetail> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<Userdetail> userList) {
		this.userList = userList;
	}

	public LiveData<String> getFragmentSwitcherLiveData(){
		return fragmentSwitcherLiveData;
	}

	public void  gotoFragment(String fragmentName){
		fragmentSwitcherLiveData.postValue(fragmentName);
	}

	//check for the search keyword
	public ArrayList<Userdetail> filter(String search, ArrayList<Userdetail> userList) {
		ArrayList<Userdetail> temp = new ArrayList<>();
		for (Userdetail ud : userList) {
			if (ud.getName().toLowerCase().contains(search.toLowerCase())) {
				temp.add(ud);
			}
			else if (ud.getRole().toLowerCase().contains(search.toLowerCase())) {
				temp.add(ud);
			}
			else if (ud.getLocation().toLowerCase().contains(search.toLowerCase())) {
				temp.add(ud);
			}
			else if (ud.getId().toLowerCase().contains(search.toLowerCase())) {
				temp.add(ud);
			}
			else if (ud.getdate().toLowerCase().contains(search.toLowerCase())) {
				temp.add(ud);
			}
			else if (ud.getSalary().toLowerCase().contains(search.toLowerCase())) {
				temp.add(ud);
			}
		}
		return temp;
	}

	public int getCurrentPos() {
		return currentPos;
	}

	public void setCurrentPos(int currentPos) {
		this.currentPos = currentPos;
	}

	//store image bitmap in the model
	public void updateImage(Bitmap bitmap){

		//fecthing obj from list
		Userdetail userdetail = userList.get(currentPos);

		userList.remove(userdetail);

		userdetail.setBitmap(bitmap);

		userList.add(currentPos,userdetail);
	}
}
