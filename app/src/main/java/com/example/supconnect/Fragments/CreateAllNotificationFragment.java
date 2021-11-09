package com.example.supconnect.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.supconnect.API.AnnouncementService;
import com.example.supconnect.API.RetrofitClient;
import com.example.supconnect.R;
import com.example.supconnect.RetrofitResponse.StatusResponse;
import com.google.android.material.chip.ChipGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

public class CreateAllNotificationFragment extends Fragment {
    private View view;
    private ImageView btnAddFile;
    private Button yes, no;
    private EditText edtTitle, edtContent;
    private ChipGroup chipGroup;
    private RelativeLayout relativeLayout;
    private ProgressDialog dialog;
    private TextView file_path;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String FilePath = data.getData().getPath();
                file_path.setVisibility(View.VISIBLE);
                file_path.setText(FilePath);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_all_notification_fragment, container, false);
        btnAddFile = view.findViewById(R.id.btn_addFile);
        yes = view.findViewById(R.id.btn_yes_notification);
        no = view.findViewById(R.id.btn_no_notification);
        edtTitle = view.findViewById(R.id.title_notification);
        edtContent = view.findViewById(R.id.content_notification);
        relativeLayout = view.findViewById(R.id.relativelayout3_all);
        chipGroup = view.findViewById(R.id.chipGroup);
        file_path = view.findViewById(R.id.txt_file_path);
        yes.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String description = edtContent.getText().toString();
            if (!(title.isEmpty() || description.isEmpty())) {
                createAnnouncement(title, description, "");
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        btnAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.putExtra(Intent.EXTRA_STREAM, uri);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, 1);
            }
        });
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        return view;
    }

    private void createAnnouncement(String title, String description, String create_date) {
        dialog.setMessage("Đang tiến hành");
        dialog.show();
        Retrofit retrofit = new RetrofitClient().getRetrofit(getContext());
        retrofit.create(AnnouncementService.class).createAnnouncement("1", title, description, create_date)
                .enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        Log.d("CREATE_LEAVE_ANNOUNCE: ", response.message());
                        dialog.dismiss();
                        edtTitle.setText("");
                        edtContent.setText("");
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Log.d("CREATE_LEAVE_ANNOUNCE_ERR: ", t.getMessage());
                        dialog.dismiss();
                        edtTitle.setText("");
                        edtContent.setText("");
                    }
                });

    }

}
