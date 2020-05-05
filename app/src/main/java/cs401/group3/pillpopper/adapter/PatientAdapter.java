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

/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: The RecyclerView adapter for Patient objects
 */
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    /**
     * The List of patients
     */
    private List<Patient> patient;

    /**
     * The listener for clicking patient objects
     */
    private OnPatientListener mOnPatientListener;

    /**
     * The adapter constructor
     * @param patient the list of patients
     * @param onPatientListener the listener for clicking patient objects
     */
    public PatientAdapter(List<Patient> patient, OnPatientListener onPatientListener) {
        this.patient = patient;
        this.mOnPatientListener = onPatientListener;
    }

    /**
     * Inflate the ViewHolder for the Recycler
     * @param parent the ViewGroup hosting the Recycler
     * @param viewType the type of views for the Recycler
     * @return a new ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient,parent,false);
        return new ViewHolder(view,mOnPatientListener);
    }

    /**
     * Bind an item to the ViewHolder
     * @param holder the ViewHolder for the item
     * @param position the position for the item
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient onePatient = patient.get(position);
        int tempImage = R.drawable.default_profile;

        holder.mPatientProfile.setImageResource(tempImage);
        holder.mPatientName.setText(onePatient.get_user_name());
        holder.mPatientDescription.setText(onePatient.get_personal_description());
    }

    /**
     * Get the item count in the RecyclerView
     * @return the total amount of patients
     */
    @Override
    public int getItemCount() {
        return patient.size();
    }

    /**
     * The ViewHolder class for Patients
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * The patient profile for the item
         */
        ImageView mPatientProfile;

        /**
         * The patient name for the item
         */
        TextView mPatientName;

        /**
         * The patient description for the item
         */
        TextView mPatientDescription;

        /**
         * The listener for clicking on a patient
         */
        OnPatientListener onPatientListener;

        /**
         * Constructor for this ViewHolder class
         * @param itemView the view of the item
         * @param onPatientListener the listener for when the item is clicked
         */
        public ViewHolder(@NonNull View itemView, OnPatientListener onPatientListener) {
            super(itemView);

            mPatientProfile = (ImageView) itemView.findViewById(R.id.patient_profile);
            mPatientName = (TextView) itemView.findViewById(R.id.patient_name);
            mPatientDescription = (TextView) itemView.findViewById(R.id.patient_description);

            this.onPatientListener = onPatientListener;

            itemView.setOnClickListener(this);
        }

        /**
         * When the item is clicked
         * @param v the parent view of the item
         */
        @Override
        public void onClick(View v) {
            onPatientListener.onPatientClick(getAdapterPosition());
        }
    }

    /**
     * The interface for the patient listener
     */
    public interface OnPatientListener{
        void onPatientClick (int position);
    }
}
