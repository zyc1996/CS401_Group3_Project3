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

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {

    private List<Prescription> prescription;
    private RecyclerViewClickListener mRecyclerViewClickListener;

    public interface RecyclerViewClickListener{
        void recyclerViewListClicked(int position);
    }

    public PrescriptionAdapter(List<Prescription> prescription, RecyclerViewClickListener recyclerViewClickListener) {
        this.prescription = prescription;
        this.mRecyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prescription,parent,false);
        return new ViewHolder(view, mRecyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prescription onePrescription = prescription.get(position);

        if(onePrescription.get_Start_time().isEmpty()) {
            holder.mPrescriptionStartTime.setText("Un-timed Prescription");
        }else{
            holder.mPrescriptionStartTime.setText(onePrescription.get_Start_time());
        }
        holder.mPrescriptionDescription.setText(onePrescription.get_content());
    }

    @Override
    public int getItemCount() {
        return prescription.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mPrescriptionStartTime;
        TextView mPrescriptionDescription;

        RecyclerViewClickListener recyclerViewClickListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);

            mPrescriptionStartTime = (TextView) itemView.findViewById(R.id.prescription_start_time);
            mPrescriptionDescription = (TextView) itemView.findViewById(R.id.prescription_description);
            this.recyclerViewClickListener = recyclerViewClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.recyclerViewListClicked(getAdapterPosition());
        }
    }

    public void refreshList(List<Prescription> prescription){
        this.prescription.clear();
        this.prescription.addAll(prescription);
        notifyDataSetChanged();
    }
}
