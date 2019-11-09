package com.baetiong.baetiong_labexer5;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] companyName, country, industry, ceoName, desc;
    int[] logo = {R.drawable.bankofchina, R.drawable.royaldutchshell, R.drawable.wellsfargo, R.drawable.exxonmobil,
            R.drawable.atandt, R.drawable.samsung, R.drawable.citigroup, R.drawable.icbc, R.drawable.jpmorgan, R.drawable.chinaconstructionbank, R.drawable.agriculturalbankofchina, R.drawable.bankofamerica,
            R.drawable.apple, R.drawable.pinganinsurance};
    ListView lstCompany;
    ArrayList<Company> companies = new ArrayList<Company>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        companyName = getResources().getStringArray(R.array.company_name);
        country = getResources().getStringArray(R.array.country);
        industry = getResources().getStringArray(R.array.industry);
        ceoName = getResources().getStringArray(R.array.ceo_name);
        desc = getResources().getStringArray(R.array.description);

        for (int i = 0; i < companyName.length; i++) {
            companies.add(new Company(logo[i], companyName[i], country[i], industry[i], ceoName[i], desc[i]));
        }
        CompanyAdapter adapter = new CompanyAdapter(this, R.layout.item, companies);
        lstCompany = findViewById(R.id.lvCompany);
        lstCompany.setAdapter(adapter);
        lstCompany.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        final File file = new File(folder, "companies.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String choice_name = companies.get(i).getName();
            String choice_country = companies.get(i).getCountry();
            String choice_industry = companies.get(i).getIndustry();
            String choice_ceo = companies.get(i).getCEO();
            fos.write(choice_name.getBytes());
            fos.write(choice_country.getBytes());
            fos.write(choice_industry.getBytes());
            fos.write(choice_ceo.getBytes());
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setIcon(logo[i]);
            dialog.setTitle(companyName[i]);
            dialog.setMessage(desc[i]);
            dialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int position) {
                    dialog.dismiss();
                    String data = "";
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        int token;
                        while ((token = fis.read()) != -1) {
                            data = data + (char) token;
                        }
                    } catch (FileNotFoundException fne) {

                    } catch (IOException io) {

                    }
                    Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                }
            });
            dialog.create().show();
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "File Not Found", Toast.LENGTH_LONG).show();
        } catch (IOException io) {
            Toast.makeText(MainActivity.this, "IO Exception", Toast.LENGTH_LONG).show();
        }

    }
}