package cs401.group3.pillpopper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs401.group3.pillpopper.R;
import cs401.group3.pillpopper.data.Prescription;

public class prescriptionAdapter extends RecyclerView.Adapter<prescriptionAdapter.ViewHolder> {

    private List<Prescription> prescription;
    private Context context;

    public prescriptionAdapter(List<Prescription> prescription) {
        this.prescription = prescription;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prescription,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prescription onePrescription = prescription.get(position);

        holder.mPrescriptionStartTime.setText(onePrescription.getStart_time());
        holder.mPrescriptionDescription.setText(onePrescription.get_content());
    }

    @Override
    public int getItemCount() {
        return prescription.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mPrescriptionStartTime;
        public TextView mPrescriptionDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mPrescriptionStartTime = (TextView) itemView.findViewById(R.id.prescription_start_time);
            mPrescriptionDescription = (TextView) itemView.findViewById(R.id.prescription_description);
        }
    }
}
