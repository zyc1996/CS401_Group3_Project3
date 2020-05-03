package cs401.group3.pillpopper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Patient;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private List<Patient> patient;
    private OnPatientListener mOnPatientListener;

    public PatientAdapter(List<Patient> patient, OnPatientListener onPatientListener) {
        this.patient = patient;
        this.mOnPatientListener = onPatientListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient,parent,false);
        return new ViewHolder(view,mOnPatientListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient onePatient = patient.get(position);
        int tempImage = R.drawable.default_profile;

        holder.mPatientProfile.setImageResource(tempImage);
        holder.mPatientName.setText(onePatient.get_user_name());
        holder.mPatientDescription.setText(onePatient.get_personal_description());
    }

    @Override
    public int getItemCount() {
        return patient.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         ImageView mPatientProfile;
         TextView mPatientName;
         TextView mPatientDescription;
        OnPatientListener onPatientListener;

        public ViewHolder(@NonNull View itemView, OnPatientListener onPatientListener) {
            super(itemView);

            mPatientProfile = (ImageView) itemView.findViewById(R.id.patient_profile);
            mPatientName = (TextView) itemView.findViewById(R.id.patient_name);
            mPatientDescription = (TextView) itemView.findViewById(R.id.patient_description);

            this.onPatientListener = onPatientListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPatientListener.onPatientClick(getAdapterPosition());
        }
    }

    //detect click
    public interface OnPatientListener{
        void onPatientClick (int position);
    }
}
