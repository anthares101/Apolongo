package com.apolongo.apolongo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apolongo.apolongo.DB.Card;

import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    class CardViewHolder extends RecyclerView.ViewHolder{
        private final TextView cardItemView;

        private CardViewHolder(View itemView){
            super(itemView);
            cardItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Card> mCards; //Cached copy of words

    CardListAdapter(Context context){mInflater = LayoutInflater.from(context);}

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position){
        if(mCards != null){
            Card current = mCards.get(position);
            holder.cardItemView.setText(current.getCardName());
        } else{
            holder.cardItemView.setText("No name");
        }
    }

    void setCards(List<Card> cards){
        mCards = cards;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mCards has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount(){
        if(mCards != null)
            return mCards.size();
        else return 0;
    }
}
