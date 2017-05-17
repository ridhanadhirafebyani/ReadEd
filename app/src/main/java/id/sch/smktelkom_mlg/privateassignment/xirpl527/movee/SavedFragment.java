package id.sch.smktelkom_mlg.privateassignment.xirpl527.movee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class SavedFragment extends Fragment {

    ArrayList<SavedItemList> wList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSaved);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        wList = new ArrayList<>();

        adapter = new SavedAdapter(wList, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

        wList.addAll(SavedItemList.listAll(SavedItemList.class));
        adapter.notifyDataSetChanged();

        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            actionView();
        } else {

        }
    }

    private void actionView() {
        wList.clear();
        wList.addAll(SavedItemList.listAll(SavedItemList.class));
        adapter.notifyDataSetChanged();
    }
}