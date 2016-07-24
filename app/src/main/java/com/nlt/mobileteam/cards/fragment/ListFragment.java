package com.nlt.mobileteam.cards.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.nlt.mobileteam.cards.R;
import com.nlt.mobileteam.cards.activity.ScrollingActivity;
import com.nlt.mobileteam.cards.adapter.BasicListAdapter;
import com.nlt.mobileteam.cards.controller.StorageController;
import com.nlt.mobileteam.cards.model.Card;
import com.nlt.mobileteam.cards.model.Folder;
import com.nlt.mobileteam.cards.widget.ItemTouchHelperClass;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView foldersRecyclerView;

    public ListFragment() {
        // Required empty public constructor
    }

    private BasicListAdapter adapter;
    public ItemTouchHelper itemTouchHelper;


    private ArrayList<Folder> foldersArrayList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        foldersRecyclerView = (RecyclerView) view.findViewById(R.id.foldersRecyclerView);
        foldersArrayList = StorageController.getInstance().getFolderFromStorage();
        adapter = new BasicListAdapter(getActivity(), foldersArrayList, foldersRecyclerView);
        adapter.setOnItemClickListener((ScrollingActivity) getActivity());

        //  foldersRecyclerView.setHasFixedSize(true);
        foldersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        foldersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(getContext(), adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(foldersRecyclerView);
        foldersRecyclerView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        final AppCompatEditText edittext = new AppCompatEditText(getContext());

        alert.setTitle(getContext().getString(R.string.Name_cat));

        alert.setView(edittext);

        alert.setPositiveButton(getContext().getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                Editable YouEditTextValue = edittext.getText();
                //OR
                String name = edittext.getText().toString();


                int color = ColorGenerator.MATERIAL.getRandomColor();
                Folder item = new Folder(name);
                item.setColor(color);
                ArrayList<Card> defaultCards = new ArrayList<Card>();
                defaultCards.add(new Card());
                item.setCards(defaultCards);
                boolean existed = false;
                for (int i = 0; i < foldersArrayList.size(); i++) {
                    if (item.getIdentifier().equals(foldersArrayList.get(i).getIdentifier())) {
                        foldersArrayList.set(i, item);
                        existed = true;
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                if (!existed) {
                    addToDataStore(item);
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();


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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void addToDataStore(Folder item) {
        foldersArrayList.add(item);
        adapter.notifyItemInserted(foldersArrayList.size() - 1);
        StorageController.getInstance().saveFolders(foldersArrayList);
    }
}
