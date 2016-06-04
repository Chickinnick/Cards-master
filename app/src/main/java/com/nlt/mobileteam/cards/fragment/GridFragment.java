package com.nlt.mobileteam.cards.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.adapter.BasicGridAdapter;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.model.Folder;
import com.nlt.mobileteam.cards.widget.ItemTouchHelperClass;

import java.util.ArrayList;

public class GridFragment extends Fragment implements BasicGridAdapter.OnItemClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FOLDER = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Folder currentFolder;

    private OnGridFragmentInteractionListener mListener;
    private RecyclerView foldersRecyclerView;
    private ArrayList<Card> cardsArrayList;
    private BasicGridAdapter gridAdapter;
    private ItemTouchHelper itemTouchHelper;

    public GridFragment() {
        // Required empty public constructor
    }

    public static GridFragment newInstance(Folder folder) {
        GridFragment fragment = new GridFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FOLDER, folder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentFolder = getArguments().getParcelable(ARG_FOLDER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        foldersRecyclerView = (RecyclerView) view.findViewById(R.id.cardsRecyclerView);
        cardsArrayList = currentFolder.getCards();
        gridAdapter = new BasicGridAdapter(getContext(), cardsArrayList);//TODO create adapter
        gridAdapter.setOnItemClickListener(this);

        //  foldersRecyclerView.setHasFixedSize(true);
        foldersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        foldersRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(gridAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(foldersRecyclerView);
        foldersRecyclerView.setAdapter(gridAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGridFragmentInteractionListener) {
            mListener = (OnGridFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onFabClicked(View view) {

    }

    @Override
    public void onItemClick(Card card, int adapterPosition) {
        mListener.onFragmentInteraction(currentFolder, card, adapterPosition);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnGridFragmentInteractionListener {
        // TODO: Update argument type and name

        void onFragmentInteraction(Folder folder, Card card, int adapterPosition);
    }
}
