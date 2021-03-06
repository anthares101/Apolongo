package com.apolongo.apolongo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apolongo.apolongo.Auxiliar.MonthConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CycleListAdapter extends RecyclerView.Adapter<CycleListAdapter.CycleViewHolder> {
    class CycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView mCycleItemView;
        private ItemClickListener mItemClickListener;

        private CycleViewHolder(View itemView){
            super(itemView);
            mCycleItemView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            //An OnClick listener is configured
            setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(final View view, int position, boolean isLongClick) {
                    if (isLongClick) {//Long click will allow user to delete a card
                        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Borrar ciclo seleccionado");
                        final int position_copy = position; //This variable is to evade an error
                        builder.setMessage("Eliminará definitivamente este ciclo y compras asociadas");
                        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mViewModel.deletePurchasesFromCycle(mCycles.get(position_copy).getStart(),
                                        mCycles.get(position_copy).getFinish(),
                                        mCycles.get(position_copy).getCardId());
                                notifyItemRemoved(position_copy);
                                Toast.makeText(view.getContext(), "Borrada", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {//Short click will take the user to the selected cycle info
                        Intent intent = new Intent(view.getContext(), PurchaseListActivity.class);
                        intent.putExtra("startDate", mCycles.get(position).getStart());
                        intent.putExtra("finishDate", mCycles.get(position).getFinish());
                        intent.putExtra("cardId", mCycles.get(position).getCardId());
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }

        //Set a listener for both Long and short click in items
        private void setItemClickListener(ItemClickListener itemClickListener){
            this.mItemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view){
            mItemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view){
            mItemClickListener.onClick(view, getAdapterPosition(), true);
            return true;
        }
    }

    private final LayoutInflater mInflater;
    private List<Cycle> mCycles; //Cached copy of cycles
    private ApolongoViewModel mViewModel;

    //We use the viewmodel to remove a Card Later
    CycleListAdapter(Context context, ApolongoViewModel viewModel){
        mInflater = LayoutInflater.from(context);
        mViewModel = viewModel;
    }

    @Override
    public CycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CycleViewHolder holder, int position){
        if(mCycles != null){
            Cycle current = mCycles.get(position);
            DateFormat format = new SimpleDateFormat("dd / MM / yyyy", Locale.ENGLISH);

            String[] dateStart = format.format(current.getStart()).split("/");
            String[] dateEnd = format.format(current.getFinish()).split("/");

            String month = MonthConverter.getStringMonth(Integer.parseInt(dateEnd[1].replaceAll(" ", "")));
            String year = dateEnd[2].replaceAll(" ", "");
            float total = mViewModel.getTotalPriceFromCycle(current.getStart(), current.getFinish(), current.getCardId());

            String content = month + " " + year + "\nTotal: " + Float.toString(total) + "€";
            //String content = format.format(current.getStart()) + " - " + format.format(current.getFinish()); //Just if cycles have to be checked the code is kept here

            holder.mCycleItemView.setText(content);
        } else{
            holder.mCycleItemView.setText(R.string.no_dataYet);
        }
    }

    void setCycles(List<Cycle> cycles){
        mCycles = cycles;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mCards has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount(){
        if(mCycles != null)
            return mCycles.size();
        else return 0;
    }

}
