package com.muhammadalikarami.smartplug;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muhammadalikarami.smartplug.models.AlarmStatus;
import com.muhammadalikarami.smartplug.objects.Alarm;
import com.muhammadalikarami.smartplug.utility.CustomTextView;

import java.util.ArrayList;

/**
 * Created by moden pal on 8/30/2015.
 */
public class AdapterAlarms extends ArrayAdapter<Alarm> {

    private Context context;
    private FragmentControlPlugs fragment;
    private ArrayList<Alarm> Alarms;

    public AdapterAlarms(Context context, FragmentControlPlugs fragment, ArrayList<Alarm> Alarms) {
        super(context, R.layout.row_alarm, Alarms);

        this.context = context;
        this.fragment = fragment;
        this.Alarms = Alarms;

    }

    public class ViewHolder {
        RelativeLayout rlRow ;
        CustomTextView txtTime;
        CustomTextView txtPlug;
        CustomTextView txtPower;
        ImageView imgTime;
        ImageView imgPlug;
        ImageView imgPower;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ViewHolder holder ;
        final Alarm curItem = getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_alarm, parent, false);

            holder.rlRow = (RelativeLayout) convertView.findViewById(R.id.rlRow);
            holder.txtTime = (CustomTextView) convertView.findViewById(R.id.txtTime);
            holder.txtPlug = (CustomTextView) convertView.findViewById(R.id.txtPlug);
            holder.txtPower = (CustomTextView) convertView.findViewById(R.id.txtPower);
            holder.imgTime = (ImageView) convertView.findViewById(R.id.imgTime);
            holder.imgPlug = (ImageView) convertView.findViewById(R.id.imgPlug);
            holder.imgPower = (ImageView) convertView.findViewById(R.id.imgPower);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set objects
        holder.txtTime.setText(curItem.getExecuteTime() + "");
        holder.txtPlug.setText(fragment.getPlugName(curItem.getPlugNum()));
        if (curItem.getAlarmStatus().equals(AlarmStatus.ON)) {
            holder.txtTime.setTextColor(context.getResources().getColor(R.color.main_blue));
            holder.txtPlug.setTextColor(context.getResources().getColor(R.color.main_blue));
            holder.txtPower.setTextColor(context.getResources().getColor(R.color.main_blue));
            holder.txtPower.setText(Utility.getContext().getString(R.string.xml_on));
            holder.imgTime.setImageResource(R.drawable.img_time_on);
            holder.imgPlug.setImageResource(R.drawable.img_plug_on);
            holder.imgPower.setImageResource(R.drawable.img_power_on);
        }
        else {
            holder.txtTime.setTextColor(context.getResources().getColor(R.color.main_black));
            holder.txtPlug.setTextColor(context.getResources().getColor(R.color.main_black));
            holder.txtPower.setTextColor(context.getResources().getColor(R.color.main_black));
            holder.txtPower.setText(Utility.getContext().getString(R.string.xml_off));
            holder.imgTime.setImageResource(R.drawable.img_time_off);
            holder.imgPlug.setImageResource(R.drawable.img_plug_off);
            holder.imgPower.setImageResource(R.drawable.img_power_off);
        }

        // set listeners
        holder.rlRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Dialog dialog = new Dialog(fragment.getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_delete);
                TextView txtContent = (TextView) dialog.findViewById(R.id.txtContent);
                TextView txtYes = (TextView) dialog.findViewById(R.id.txtYes);
                TextView txtCancel = (TextView) dialog.findViewById(R.id.txtCancel);
                dialog.show();

                txtYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragment.cancelAlarmRequest(position);
                        dialog.dismiss();
                    }
                });

                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                return false;
            }
        });

        return convertView;
    }
}
