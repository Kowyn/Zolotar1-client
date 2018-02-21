package com.example.renovikov.zolotarclient1;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ObjectPreferencesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ObjectPreferencesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectPreferencesFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MODE = "MODE";

    // TODO: Rename and change types of parameters
    private String mMode;
    private String nameEntries;


    TextView tv_obj_title;

    EditText obj_name;
    EditText obj_adress;
    EditText obj_district;
    EditText et_obj_to_point;
    EditText et_obj_volume;

    Button fr_bt_gps;
    Button fr_bt_confirm;
    Button fr_bt_cancel;

    SQLiteDatabase db;
    DBhelper dbhelper;

    ContentValues vals;

    private OnFragmentInteractionListener mListener;

    public ObjectPreferencesFragment() {
        // Required empty public constructor
    }

    public static ObjectPreferencesFragment newInstance(String mode) {
        ObjectPreferencesFragment fragment = new ObjectPreferencesFragment();
        Bundle args = new Bundle();

        args.putString(MODE, mode);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMode = getArguments().getString(MODE);
            }
        //попытаемся забрать первоначальное имя, что бы потом найти запись в БД для обновления
        nameEntries = obj_name.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = (View) inflater.inflate(R.layout.fragment_object_preferences,null,false);
        tv_obj_title = (TextView) v.findViewById(R.id.tv_obj_title);
        obj_name = (EditText) v.findViewById(R.id.OBJ_name);
        obj_adress = (EditText) v.findViewById(R.id.OBJ_adress);
        obj_district = (EditText) v.findViewById(R.id.OBJ_district);
        et_obj_to_point = (EditText) v.findViewById(R.id.et_obj_to_point);
        et_obj_volume = (EditText) v.findViewById(R.id.et_obj_volume);
        fr_bt_gps = (Button) v.findViewById(R.id.fr_bt_gps);
        fr_bt_confirm = (Button) v.findViewById(R.id.fr_bt_confirm);
        fr_bt_cancel = (Button) v.findViewById(R.id.fr_bt_cancel);

        fr_bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //обработка подтверждения редактирования
                SaveEntered();
            }
        });

        fr_bt_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //запуск обработки карты
            }
        });

        fr_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //возврат в активити без сохранения
            }
        });

        return v;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void SaveEntered(){
        db = dbhelper.getWritableDatabase();
        vals = new ContentValues();

        vals.put(dbhelper.OBJ_NAME, obj_name.getText().toString());
        vals.put(dbhelper.OBJ_ADRESS, obj_adress.getText().toString());
        vals.put(dbhelper.OBJ_DISTRICT, obj_district.getText().toString());
        vals.put(dbhelper.OBJ_RANGE, et_obj_to_point.getText().toString());
        vals.put(dbhelper.OBJ_VOLUME, et_obj_volume.getText().toString());
        switch (mMode) {
            case "ADD":
                db.insert(dbhelper.TABLE_NAME, null, vals);
            case "EDIT":
                db.update(dbhelper.TABLE_NAME, vals,dbhelper.OBJ_NAME+" = ?",new String[] {nameEntries});
        }
        dbhelper.close();
    }


}


