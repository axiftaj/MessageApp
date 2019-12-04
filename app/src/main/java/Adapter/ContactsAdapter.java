package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.R;

import java.util.ArrayList;
import java.util.List;

import Model.ContactsModel;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<ContactsModel> contactsModels ;
    Context context ;
    public static List<String> selected_contacts = new ArrayList<>();

    public ContactsAdapter(List<ContactsModel> contactsModels, Context context) {
        this.contactsModels = contactsModels;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            final ContactsModel pos = contactsModels.get(position);

            holder.name.setText(pos.getName());
            holder.number.setText(pos.getNumber());

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked){
                        Log.d("selected" , String.valueOf(selected_contacts.add(pos.getNumber())));
                    }else {
                        selected_contacts.remove(pos.getNumber());
                        Log.d("selected" , String.valueOf(selected_contacts.remove(pos.getNumber())));

                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return contactsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name , number ;
        CheckBox checkBox ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            checkBox = itemView.findViewById(R.id.checkbox);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
