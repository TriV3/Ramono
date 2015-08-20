package com.TriVe.Apps.Ramono;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.TriVe.Apps.Ramono.CustomViews.NestedListView;
import com.TriVe.Apps.Ramono.Ramonage.Chauffage;
import com.TriVe.Apps.Ramono.Ramonage.Client;
import com.TriVe.Apps.Ramono.Ramonage.Conduit;
import com.TriVe.Apps.Ramono.Toolz.Toolz;
import com.TriVe.Apps.Ramono.adapters.AddressSpinnerAdapter;
import com.TriVe.Apps.Ramono.adapters.ChauffagesListAdapter;
import com.TriVe.Apps.Ramono.adapters.ConduitsListAdapter;
import com.TriVe.Apps.Ramono.adapters.EmailSpinnerAdapter;
import com.TriVe.Apps.Ramono.adapters.TelSpinnerAdapter;
import com.TriVe.Apps.mycontact.ContactAPI.ContactAPI;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Address;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Contact;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Email;
import com.TriVe.Apps.mycontact.ContactAPI.objects.Phone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class ClientInfoFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, TextWatcher
{
    public static final String TAG = "ClientInfoFragmentTAG";

    private EditText etName, etElemTuyau, etComment;
    private NestedListView lvConduits, lvChauffages;
    private ConduitsListAdapter conduitsListAdapter;
    private ChauffagesListAdapter chauffagesListAdapter;

    private Spinner spinTel, spinAdress, spinEmail;
    private AddressSpinnerAdapter addressSpinnerAdapter;
    private EmailSpinnerAdapter emailSpinnerAdapter;
    private TelSpinnerAdapter telSpinnerAdapter;

    private Button btnSaveNote;

    private List<Conduit>conduits = new ArrayList<>();
    private List<Chauffage>chauffages = new ArrayList<>();

    private List<Email> emails = new ArrayList<>();
    private List<Phone> phones = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();

    private Context context;


    static final int INTENT_CONTACT_EDIT = 1;


    public ClientInfoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        Datas.isNoteModified = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_client_info, container, false);


        etName = (EditText) v.findViewById(R.id.etClientName);


        spinAdress = (Spinner)v.findViewById(R.id.spinAddress);
        addressSpinnerAdapter = new AddressSpinnerAdapter(context, R.layout.address_spinner_row, addresses);
        spinAdress.setAdapter(addressSpinnerAdapter);

        spinEmail = (Spinner)v.findViewById(R.id.spinEMail);
        emailSpinnerAdapter = new EmailSpinnerAdapter(context, R.layout.email_spinner_row, emails);
        spinEmail.setAdapter(emailSpinnerAdapter);

        spinTel = (Spinner)v.findViewById(R.id.spinTel);
        telSpinnerAdapter = new TelSpinnerAdapter(context, R.layout.tel_spinner_row, phones);
        spinTel.setAdapter(telSpinnerAdapter);

        lvConduits = (NestedListView) v.findViewById(R.id.lvConduits);
        conduitsListAdapter = new ConduitsListAdapter(getActivity(),conduits);
        lvConduits.setAdapter(conduitsListAdapter);
        lvConduits.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvConduits.setItemsCanFocus(false);
        lvConduits.setOnItemClickListener(this);

        lvChauffages = (NestedListView) v.findViewById(R.id.lvChauffage);
        chauffagesListAdapter = new ChauffagesListAdapter(getActivity(), chauffages);
        lvChauffages.setAdapter(chauffagesListAdapter);
        lvChauffages.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvChauffages.setItemsCanFocus(false);
        lvChauffages.setOnItemClickListener(this);

        etElemTuyau = (EditText)v.findViewById(R.id.etElemTuyau);
        etElemTuyau.addTextChangedListener(this);
        etComment = (EditText)v.findViewById(R.id.etComment);
        etComment.addTextChangedListener(this);

        btnSaveNote = (Button)v.findViewById(R.id.btnSaveNote);
        btnSaveNote.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        context = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(parent == lvConduits)
        {
            conduits.get(position).toggleSelected();
            conduitsListAdapter.notifyDataSetChanged();
        }
        if(parent == lvChauffages)
        {
            for(Chauffage c : chauffages)
                c.setSelected(false);
            chauffages.get(position).setSelected(true);
            chauffagesListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v == btnSaveNote)
        {
            if(Datas.isNoteModified)
            {
                saveNote();
                Datas.isNoteModified = false;
            }
        }
    }

    private void saveNote()
    {
        String entireNote = "";
        if (Datas.NoteElements.size() > 1)
            Datas.NoteElements.set(1, DatasToNote());
        else
            Datas.NoteElements.add(DatasToNote());

        for (String s : Datas.NoteElements)
        {
            entireNote += s;
        }

        if (Datas.Selectedclient.getNotes().size() > 0)
            Datas.Selectedclient.getNotes().set(0, entireNote);
        else
            Datas.Selectedclient.getNotes().add(entireNote);

        Datas.Selectedclient.updateClient(context);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        // FIXME : triggered when contact is edited...
        Datas.isNoteModified = true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.client_info_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;
        Client c = Datas.Selectedclient;

        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, c.getId());

        switch (item.getItemId())
        {

            //region Client
            case R.id.client_call_menu_item:
                if ((spinTel.getSelectedItemPosition() == -1) || (phones.size() == 0))
                {
                    Toast.makeText(context, "Pas de numéro de téléphone selectionné...", Toast.LENGTH_SHORT).show();
                    return true;
                }
                String tel = phones.get(spinTel.getSelectedItemPosition()).getNumber();
                if (!Toolz.isPhoneNumbervalid(tel))
                {
                    Toast.makeText(context, "Numéro de téléphone non valide...", Toast.LENGTH_SHORT).show();
                    return true;
                }
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                startActivity(intent);
                return true;
            // Edit Client:
            case R.id.client_edit_menu_item:
                Log.i(ClientInfoFragment.TAG + " : onOptionsItemSelected", "Client Name = " + c.getDisplayName() + ", Client ID = " + c.getId());
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.putExtra("finishActivityOnSaveCompleted", true);
                startActivityForResult(intent, INTENT_CONTACT_EDIT);
                return true;

            case R.id.client_locate_menu_item:
                if ((spinAdress.getSelectedItemPosition() == -1) || (addresses.size() == 0))
                {
                    Toast.makeText(context, "Pas d'adresse selectionnée...", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (isGoogleMapsInstalled())
                {
                    String adresse = addresses.get(spinAdress.getSelectedItemPosition()).toString();
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + adresse);
                    intent = new Intent(android.content.Intent.ACTION_VIEW, gmmIntentUri);
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, "Google Maps n'est pas installé...", Toast.LENGTH_LONG).show();
                }

                return true;

            //endregion

            //region Conduit
            case R.id.conduit_add_menu_item:
                Datas.SelectedConduit = null;

                displayConduitDialog();

                return true;

            case R.id.conduit_edit_menu_item:
                if(conduitsListAdapter.getNbOfconduitSelected() == 1)
                {
                    Conduit conduit = conduits.get(conduitsListAdapter.getSelectedConduits().get(0));
                    conduit.id = conduitsListAdapter.getSelectedConduits().get((0));
                    Datas.SelectedConduit = conduit;

                    displayConduitDialog();
                }
                else
                {
                    Toast.makeText(context, "Selectionnez un seul conduit.", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.conduit_remove_menu_item:
                if(conduitsListAdapter.getNbOfconduitSelected() == 1)
                {
                    Conduit conduit = conduits.get(conduitsListAdapter.getSelectedConduits().get(0));
                    conduits.remove(conduit);
                    conduitsListAdapter.notifyDataSetChanged();
                    Datas.isNoteModified = true;
                }
                else
                {
                    Toast.makeText(context, "Selectionnez un seul conduit.", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.conduit_MAJ_dates_menu_item:

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(cal.getTime());

                for(Conduit con : conduits)
                {
                    if (con.isSelected())
                    {
                        con.date = date;
                    }
                }
                conduitsListAdapter.notifyDataSetChanged();
                Datas.isNoteModified = true;
                return true;



            case R.id.chauffage_add_menu_item:
                Datas.SelectedChauffage = null;

                displayChauffageDialog();

                return true;
            //endregion

            //region Chauffage
            case R.id.chauffage_edit_menu_item:
                if(chauffagesListAdapter.getNbOfChauffagesSelected() == 1)
                {
                    Chauffage ch = chauffages.get(chauffagesListAdapter.getSelectedChauffages().get(0));
                    ch.id = chauffagesListAdapter.getSelectedChauffages().get((0));
                    Datas.SelectedChauffage = ch;

                    displayChauffageDialog();
                }
                else
                {
                    Toast.makeText(context, "Selectionnez un chauffage.", Toast.LENGTH_SHORT).show();
                }
                return true;


            case R.id.chauffage_remove_menu_item:
                if(chauffagesListAdapter.getNbOfChauffagesSelected() == 1)
                {
                    Chauffage cond = chauffages.get(chauffagesListAdapter.getSelectedChauffages().get(0));
                    chauffages.remove(cond);
                    chauffagesListAdapter.notifyDataSetChanged();
                    Datas.isNoteModified = true;
                }
                else
                {
                    Toast.makeText(context, "Selectionnez un chauffage.", Toast.LENGTH_SHORT).show();
                }

                return true;
            //endregion

            //region Others
            case R.id.client_appointment_menu_item:
                Calendar calendar = Calendar.getInstance();
                intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);

                intent.putExtra(CalendarContract.Events.TITLE, "RDV avec \"" + Datas.Selectedclient.getDisplayName() + "\"");
//                intent.putExtra(CalendarContract.Events.DESCRIPTION, "Description");
                intent.putExtra(CalendarContract.Events.ALL_DAY, false);
                intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                // Start and stop dates:
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTime().getTime());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTime().getTime() + 3600000);

                if ((spinEmail.getSelectedItemPosition() != -1) && (emails.size() > 0))
                {
                    intent.putExtra(Intent.EXTRA_EMAIL, emails.get(spinEmail.getSelectedItemPosition()).getAddress());
                }

                if ((spinAdress.getSelectedItemPosition() != -1) && (addresses.size() != 0))
                {
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, addresses.get(spinAdress.getSelectedItemPosition()).toString());
                }

                startActivity(intent);

                return true;
            //endregion

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_CONTACT_EDIT)
        {
            displayContact();
        }
    }


    public void displayContact()
    {
        clearDatas();
        Log.i(ClientInfoFragment.TAG + " : displayContact start", "Client Name = " + Datas.Selectedclient.getDisplayName() + ", Client ID = " + Datas.Selectedclient.getId());
        Contact con = new ContactAPI(context).GetContactInfoFromID(Datas.Selectedclient.getId());
        Datas.Selectedclient = new Client(con);
        Log.i(ClientInfoFragment.TAG + " : displayContact middle", "Client Name = " + Datas.Selectedclient.getDisplayName() + ", Client ID = " + Datas.Selectedclient.getId());
        Client c = Datas.Selectedclient;

        etName.setText(c.getDisplayName());

        if (c.getPhone().size() > 0)
        {
            phones.clear();
            phones.addAll(c.getPhone());
            telSpinnerAdapter.notifyDataSetChanged();
        }
        if (c.getEmail().size() > 0)
        {
            emails.clear();
            emails.addAll(c.getEmail());
            emailSpinnerAdapter.notifyDataSetChanged();
        }
        if (c.getAddresses().size() > 0)
        {
            addresses.clear();
            addresses.addAll(c.getAddresses());
            addressSpinnerAdapter.notifyDataSetChanged();
        }
        if (c.getNotes().size() > 0)
        {
            conduits.clear();
            chauffages.clear();
            NotesToData(c.getNotes());
        }

        Log.i(ClientInfoFragment.TAG + " : displayContact end", "Client Name = " + Datas.Selectedclient.getDisplayName() + ", Client ID = " + Datas.Selectedclient.getId());
    }

    private void NotesToData(List<String> Notes)
    {
        /**
         ##
         [Conduit bois @ 1:08/13& ]
         [Conduit gaz @ ]
         [Conduit mazout @ ]
         [Element tuyau de raccordement @ 0 ]
         [Chauffage poele a bois @ 0 ]
         [Chauffage insert @ 0 ]
         [Chauffage cheminée ouverte @ 0 ]
         [Chauffage poele à mazout @ 0 ]
         [Chauffage chaudière mazout @ 0 ]
         [Chauffage fourneau @ 0 ]
         [Chauffage system cesa @ 0]
         [Commentaire @ exemple ]
         ##
         */
        // Get a list of string for all notes.
        Datas.NoteElements = NotesManagement.separateConduitFromNote(Notes);
        if (Datas.NoteElements.size() < 2)
            return;

        List<String> datas = NotesManagement.getLinesFromNotes(Datas.NoteElements.get(1));

        for(String s : datas)
        {
            if (s.toLowerCase().contains("conduit"))
            {
                // Get conduits from line:
                conduits.addAll(NotesManagement.getConduitsFromLine(s));
                conduitsListAdapter.notifyDataSetChanged();
            }
            else if (s.toLowerCase().contains("element"))
            {
                etElemTuyau.setText(NotesManagement.getStringFromLine(s).replace(" ", ""));
            }
            else if (s.toLowerCase().contains("chauffage"))
            {
                Chauffage c = NotesManagement.getChauffageFromLine(s);
                if(c != null)
                {
                    chauffages.add(c);
                    chauffagesListAdapter.notifyDataSetChanged();
                }
            }
            else if (s.toLowerCase().contains("commentaire"))
            {
                etComment.setText(NotesManagement.getStringFromLine(s));
            }

        }
    }

    private String DatasToNote()
    {
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String Conduit_Types = mySharedPreferences.getString("conduits", getResources().getString(R.string.Conduit_Types));
        String Chauffage_Types = mySharedPreferences.getString("chauffages", getResources().getString(R.string.Chauffage_Types));

        String output = "";
        List<String>conduitTypes = new ArrayList<>(Arrays.asList(Conduit_Types.split(";")));
        List<String>chauffageTypes = new ArrayList<>(Arrays.asList(Chauffage_Types.split(";")));

        output += "##\n";
        // Conduits part of the note:
        for(String s : conduitTypes)
        {
            output += "[Conduit " + s + "@ ";
            for(Conduit c : conduits)
            {
                if(c.type.toLowerCase().equals( s.toLowerCase()))
                {
                    output += c.place + ":" + c.date + "&";
                }
            }
            output += "]\n";
        }

        // Elements part of the note:
        String elemTuyau = etElemTuyau.getText().toString();
        if (elemTuyau.equals(""))
            elemTuyau = "0";
        output += "[Element tuyau de raccordement @ " + elemTuyau  + "]\n";

        // Chauffage part of the note:
        for(String s : chauffageTypes)
        {
            output += "[Chauffage " + s + "@ ";
            for(Chauffage c : chauffages)
            {
                if(c.type.toLowerCase().equals( s.toLowerCase()))
                    output += c.nombre;
            }
            output += "]\n";
        }

        // Comment part of the note:
        output += "[Commentaire @ " + etComment.getText().toString() + "]\n";
        output += "##";

        Log.d(ClientInfoFragment.TAG, output);

        return output;
    }

    private void clearDatas()
    {
        etName.setText("");
        emails.clear();
        phones.clear();
        addresses.clear();
        emailSpinnerAdapter.notifyDataSetChanged();
        telSpinnerAdapter.notifyDataSetChanged();
        addressSpinnerAdapter.notifyDataSetChanged();
        conduits.clear();
        conduitsListAdapter.notifyDataSetChanged();
        chauffages.clear();
        chauffagesListAdapter.notifyDataSetChanged();
    }


    private void displayConduitDialog()
    {
        final boolean isAdded = (Datas.SelectedConduit == null);

        //Custom Dialog
        final Dialog conduitDialog = new Dialog(context);
        conduitDialog.setContentView(R.layout.dialog_conduit_info);

        // Set the custom dialog components
        Button btnOK, btnCancel;
        final Spinner spinType;
        final EditText etPlace;
        final DatePicker dpDate;

        List<String> conduitTypes;

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String conduitPref = mySharedPreferences.getString("conduits", getResources().getString(R.string.Conduit_Types));


        conduitTypes = Arrays.asList(conduitPref.split(";"));

        btnOK = (Button) conduitDialog.findViewById(R.id.btnConduitOK);
        btnCancel = (Button) conduitDialog.findViewById(R.id.btnConduitCANCEL);

        etPlace = (EditText)conduitDialog.findViewById(R.id.etConduitPlace);
        dpDate = (DatePicker)conduitDialog.findViewById(R.id.dpConduitDate);


        spinType = (Spinner)conduitDialog.findViewById(R.id.spinConduitType);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, conduitTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinType.setAdapter(adapter);

        if(Datas.SelectedConduit == null)
        {
            conduitDialog.setTitle("Ajouter un conduit:");
            etPlace.setText("libellé");
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // set current date into datepicker
            dpDate.updateDate(year, month, day);
        }
        else
        {
            conduitDialog.setTitle("Editer un conduit:");
            etPlace.setText(Datas.SelectedConduit.place);
            String[]dateElem = Datas.SelectedConduit.date.split("/");
            int day = Integer.parseInt(dateElem[0]);
            int month = Integer.parseInt(dateElem[1]);
            int year = Integer.parseInt(dateElem[2]);
            // set current date into datepicker
            dpDate.updateDate(year, month - 1, day);

            spinType.setSelection(getIndex(spinType, Datas.SelectedConduit.type));
        }

        btnOK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String type = spinType.getSelectedItem().toString();
                String place = etPlace.getText().toString();

                int day = dpDate.getDayOfMonth();
                int month = dpDate.getMonth();
                int year =  dpDate.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(calendar.getTime());

                Datas.SelectedConduit = new Conduit(type, place, date);

                if (isAdded)
                {
                    conduits.add(Datas.SelectedConduit);
                    conduitsListAdapter.notifyDataSetChanged();
                }
                else
                {
                    conduits.set(conduitsListAdapter.getSelectedConduits().get(0), Datas.SelectedConduit);
                    conduitsListAdapter.notifyDataSetChanged();
                }

                Datas.isNoteModified = true;

                conduitDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Datas.SelectedConduit = null;
                conduitDialog.dismiss();
            }
        });

        conduitDialog.show();

    }

    private void displayChauffageDialog()
    {
        final boolean isAdded = (Datas.SelectedChauffage == null);

        //Custom Dialog
        final Dialog chauffageDialog = new Dialog(context);
        chauffageDialog.setContentView(R.layout.dialog_chauffage_info);

        // Set the custom dialog components
        Button btnOK, btnCancel;
        final Spinner spinType;
        final EditText etNbr;

        List<String> chauffageTypes;

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String conduitPref = mySharedPreferences.getString("chauffages",  getResources().getString(R.string.Chauffage_Types));

        chauffageTypes = Arrays.asList(conduitPref.split(";"));

        btnOK = (Button) chauffageDialog.findViewById(R.id.btnChauffageOK);
        btnCancel = (Button) chauffageDialog.findViewById(R.id.btnChauffageCANCEL);

        etNbr = (EditText)chauffageDialog.findViewById(R.id.etChauffageNumber);


        spinType = (Spinner)chauffageDialog.findViewById(R.id.spinChauffageType);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, chauffageTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinType.setAdapter(adapter);

        if(Datas.SelectedChauffage == null)
        {
            chauffageDialog.setTitle("Ajouter un chauffage:");
            etNbr.setText("0");
        }
        else
        {
            chauffageDialog.setTitle("Editer un chauffage:");
            etNbr.setText(Datas.SelectedChauffage.nombre);
            spinType.setSelection(getIndex(spinType, Datas.SelectedChauffage.type));
        }

        btnOK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String type = spinType.getSelectedItem().toString();
                String nombre = etNbr.getText().toString();

                Datas.SelectedChauffage = new Chauffage(type, nombre);

                if (isAdded)
                {
                    boolean isPresent = false;
                    for(Chauffage ch : chauffages)
                    {
                        // Check if Chauffage is already present in list
                        if (ch.type.toLowerCase().equals(Datas.SelectedChauffage.type.toLowerCase()))
                        {
                            isPresent = true;
                        }
                    }

                    if (isPresent)
                    {
                        // warning, chauffage already present.
                        WarningDialog();
                    }
                    else
                    {
                        chauffages.add(Datas.SelectedChauffage);
                        chauffagesListAdapter.notifyDataSetChanged();
                    }

                }
                else
                {
                    chauffages.set(chauffagesListAdapter.getSelectedChauffages().get(0), Datas.SelectedChauffage);
                    chauffagesListAdapter.notifyDataSetChanged();
                }

                Datas.isNoteModified = true;

                chauffageDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Datas.SelectedChauffage = null;
                chauffageDialog.dismiss();
            }
        });

        chauffageDialog.show();

    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Warn if an added chuffage is already present.
     */
    private void WarningDialog()
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setIcon(android.R.drawable.ic_dialog_alert);
        ad.setTitle("Attention!");
        ad.setMessage("Chauffage est déja présent?");
        ad.setNeutralButton("OK", null);

        ad.show();
    }

    public boolean isGoogleMapsInstalled()
    {
        try
        {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0 );
            return true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }


}
