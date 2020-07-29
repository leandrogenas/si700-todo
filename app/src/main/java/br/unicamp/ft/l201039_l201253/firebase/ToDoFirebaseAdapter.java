package br.unicamp.ft.l201039_l201253.firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.unicamp.ft.l201039_l201253.R;
import br.unicamp.ft.l201039_l201253.ToDo;
import br.unicamp.ft.l201039_l201253.sqlite.DatabaseHelper;

public class ToDoFirebaseAdapter extends FirebaseRecyclerAdapter<ToDo, RecyclerView.ViewHolder> {
    public ToDoFirebaseAdapter(@NonNull FirebaseRecyclerOptions<ToDo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,
                                    int i, @NonNull ToDo todo) {

        ((ToDoViewHolder) viewHolder).bind(todo);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                      int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_todo,
                parent, false);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView id = v.findViewById(R.id.idToDo);

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("todos").child(id.getText().toString());
                ref.removeValue();
                notifyDataSetChanged();
                Toast.makeText(parent.getContext(), "Atividade concluída", Toast.LENGTH_SHORT).show();
                //toDoViewHolderOnItemClickListener.toDoViewHolderOnItemClick(txt.getText().toString(), vh.getPosicao() );
                return false;
            }
        });

        return new ToDoViewHolder(view);
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder{
        private TextView id;
        private TextView atividade;
        private ImageView categoria;
        private TextView notificar;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idToDo);
            atividade = itemView.findViewById(R.id.atividade);
            categoria = itemView.findViewById(R.id.categoria);
            notificar = itemView.findViewById(R.id.notificar);
        }

        void bind(ToDo todo){
            id.setText(todo.getId());
            atividade.setText(todo.getAtividade());
            categoria.setImageResource(getResourceCategoria(todo.getCategoria()));
            notificar.setText(todo.getNotificar());
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
