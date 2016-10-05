package com.example.atakan_pc.kbulibrary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



/**
 * A simple {@link Fragment} subclass.
 */
public class KatalogTaramaFragment extends Fragment implements View.OnClickListener {
    private EditText txtkeyword;
    private Button btnsearch;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_katalog_tarama,container,false);
        txtkeyword = (EditText) view.findViewById(R.id.txtkeyword);
        btnsearch = (Button) view.findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnsearch){
            //txtkeyword.setText(txtkeyword.getText().toString().toUpperCase());
            Intent searchIntent = new Intent(getActivity(),ListResult.class);
            searchIntent.putExtra("keyword", txtkeyword.getText().toString());

            startActivity(searchIntent);
        }

    }
}
