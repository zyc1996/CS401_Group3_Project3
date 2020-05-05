package cs401.group3.pillpopper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Prescription;

/**
 * @author Lauren Dennedy, Yucheng Zheng, John Gilcreast, John Berge
 * @since March 2020, SDK 13
 * @version 1.0
 *
 * Purpose: The RecyclerView adapter for Prescription objects
 */
public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {
    /**
     * The List of prescriptions
     */
    private List<Prescription> prescription;

    /**
     * The listener for clicking prescription objects
     */
    private RecyclerViewClickListener mRecyclerViewClickListener;

    /**
     * The interface for the prescription listener
     */
    public interface RecyclerViewClickListener{
        void recyclerViewListClicked(int position);
    }

    /**
     * The adapter constructor
     * @param prescription the list of prescriptions
     * @param recyclerViewClickListener the listener for clicking prescription objects
     */
    public PrescriptionAdapter(List<Prescription> prescription, RecyclerViewClickListener recyclerViewClickListener) {
        this.prescription = prescription;
        this.mRecyclerViewClickListener = recyclerViewClickListener;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prescription,parent,false);
        return new ViewHolder(view, mRecyclerViewClickListener);
    }

    /**
     * Bind an item to the ViewHolder
     * @param holder the ViewHolder for the item
     * @param position the position for the item
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prescription onePrescription = prescription.get(position);

        if(onePrescription.getStartTime().isEmpty()) {
            holder.mPrescriptionStartTime.setText("Un-timed Prescription");
        }else{
            holder.mPrescriptionStartTime.setText(onePrescription.getStartTime());
        }
        holder.mPrescriptionDescription.setText(onePrescription.getContent());
    }
    /**
     * Get the item count in the RecyclerView
     * @return the total amount of prescriptions
     */
    @Override
    public int getItemCount() {
        return prescription.size();
    }

    /**
     * The ViewHolder class for Prescriptions
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        /**
         * The prescription start time for the item
         */
        TextView mPrescriptionStartTime;

        /**
         * The prescription description for the item
         */
        TextView mPrescriptionDescription;

        /**
         * The listener for clicking on a prescription
         */
        RecyclerViewClickListener recyclerViewClickListener;

        /**
         * Constructor for this ViewHolder class
         * @param itemView the view of the item
         * @param recyclerViewClickListener the listener for when the item is clicked
         */
        public ViewHolder(@NonNull View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);

            mPrescriptionStartTime = (TextView) itemView.findViewById(R.id.prescription_start_time);
            mPrescriptionDescription = (TextView) itemView.findViewById(R.id.prescription_description);
            this.recyclerViewClickListener = recyclerViewClickListener;

            itemView.setOnClickListener(this);
        }

        /**
         * When the item is clicked
         * @param v the parent view of the item
         */
        @Override
        public void onClick(View v) {
            recyclerViewClickListener.recyclerViewListClicked(getAdapterPosition());
        }
    }

    /**
     * Refresh the list of prescriptions
     * @param prescription the list of prescriptions
     */
    public void refreshList(List<Prescription> prescription){
        this.prescription.clear();
        this.prescription.addAll(prescription);
        notifyDataSetChanged();
    }
}
