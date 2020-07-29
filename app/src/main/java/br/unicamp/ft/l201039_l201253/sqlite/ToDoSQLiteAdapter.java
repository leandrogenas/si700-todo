package br.unicamp.ft.l201039_l201253.sqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.unicamp.ft.l201039_l201253.R;
import br.unicamp.ft.l201039_l201253.ToDo;

public class ToDoSQLiteAdapter extends RecyclerView.Adapter {

    private ArrayList<ToDo> toDos;
    private ToDoViewHolderOnItemClickListener toDoViewHolderOnItemClickListener;

    public interface ToDoViewHolderOnItemClickListener{
        void toDoViewHolderOnItemClick(String nome);
    }

    public ToDoSQLiteAdapter(ArrayList<ToDo> toDos) {
        this.toDos = toDos;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_todo, parent, false);

        final ToDoViewHolder vh = new ToDoViewHolder(view);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView id = v.findViewById(R.id.idToDo);
                int pos = vh.getPosicao();
                toDos.remove(pos);
                DatabaseHelper db = DatabaseHelper.getInstancia(null);
                db.excluirId(id.getText().toString());
                notifyDataSetChanged();
                Toast.makeText(parent.getContext(), "Atividade concluída", Toast.LENGTH_SHORT).show();
                //toDoViewHolderOnItemClickListener.toDoViewHolderOnItemClick(txt.getText().toString(), vh.getPosicao() );
                return false;
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ToDoViewHolder) holder).bind(toDos.get(position));
        ((ToDoViewHolder) holder).setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView atividade;
        private ImageView categoria;
        private TextView notificar;
        private int posicao;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idToDo);
            atividade = itemView.findViewById(R.id.atividade);
            categoria = itemView.findViewById(R.id.categoria);
            notificar = itemView.findViewById(R.id.notificar);

        }

        public void bind(ToDo todo) {
            id.setText(todo.getId());
            atividade.setText(todo.getAtividade());
            categoria.setImageResource(getResourceCategoria(todo.getCategoria()));
            notificar.setText(todo.getNotificar());
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }

        public int getPosicao() {
            return this.posicao;
        }

        private int getResourceCategoria(String categoria) {
            switch (categoria) {
                case "Trabalho":
                    return R.drawable.trabalho;
                case "Faculdade":
                    return R.drawable.faculdade;
                case "Hobbies":
                    return R.drawable.dado;
            }

            return R.drawable.botao;
        }
    }

}
