package br.unicamp.ft.l201039_l201253;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter {

    private ArrayList<ToDo> toDos;

    public ToDoAdapter(ArrayList<ToDo> toDos)
    {
        this.toDos = toDos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_todo, parent, false);

        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        ((ToDoViewHolder) holder).bind(toDos.get(position));
        ((ToDoViewHolder) holder).setPosicao(position);
    }

    @Override
    public int getItemCount()
    {
        return toDos.size();
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {
        private TextView atividade;
        private TextView categoria;
        private int posicao;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            atividade = itemView.findViewById(R.id.atividade);
            categoria = itemView.findViewById(R.id.categoria);
        }

        public void bind(ToDo todo){
            //imageView.setImageResource(aluno.getFoto());
            atividade.setText(todo.getAtividade());
            categoria.setText(todo.getCategoria());
//            textView.setText(
//                    textView.getContext().getResources().getString(
//                            R.string.texto_aluno,
//                            aluno.getNome())
//            );
        }

        public void setPosicao(int posicao){
            this.posicao = posicao;
        }

        public int getPosicao(){
            return this.posicao;
        }
    }

}
