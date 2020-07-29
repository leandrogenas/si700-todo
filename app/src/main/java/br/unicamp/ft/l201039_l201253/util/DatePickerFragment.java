package br.unicamp.ft.l201039_l201253.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import br.unicamp.ft.l201039_l201253.NovoToDoActivity;
import br.unicamp.ft.l201039_l201253.R;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    protected NovoToDoActivity activity;

    public DatePickerFragment(NovoToDoActivity act)
    {
        activity = act;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this, ano, mes, dia);

    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Switch sw = activity.findViewById(R.id.notificar);
        sw.setChecked(false);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
    {
        activity.setData(i, i1, i2);
    }


}
