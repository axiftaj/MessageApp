package com.example.messageapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Adapter.GroupAdapter;
import Model.GroupModel;


public class GroupsFragment extends Fragment {

    private RecyclerView recyclerView ;
    private GroupAdapter groupAdapter ;
    private List<GroupModel> groupModelList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      View view =   inflater.inflate(R.layout.fragment_groups, container, false);
      recyclerView = view.findViewById(R.id.group_recyclerView);

        groupAdapter = new GroupAdapter(groupModelList , getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(groupAdapter);
        return view ;
    }


}
