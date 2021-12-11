package ir.ttic.footweight.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.anychart.core.stock.series.Stick;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import ir.ttic.footweight.R;
import ir.ttic.footweight.adapters.NavigatioListAdapter;

public class LoginDialog extends DialogFragment {

  private Button btnLogin;
  private EditText edtUser;
  private OnLoginClick onLoginClick;

  public LoginDialog(OnLoginClick onLoginClick) {
    this.onLoginClick = onLoginClick;
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    return inflater.inflate(R.layout.dialog_login, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    edtUser =  view.findViewById(R.id.edtUser);
    btnLogin = view.findViewById(R.id.btn_login);
    btnLogin.setOnClickListener(this::onLoginClick);

    getDialog().setCancelable(false);
    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
  }

  @Override
  public void onStart() {
    super.onStart();

    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);

    getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

  }


  public void onLoginClick(View v){
    onLoginClick.onLogin(edtUser.getText().toString());
    dismiss();
  }

  public interface OnLoginClick{
    void onLogin( String userName);
  }

}
