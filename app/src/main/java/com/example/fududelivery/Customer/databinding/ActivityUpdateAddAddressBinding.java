// Generated by view binder compiler. Do not edit!
package com.example.fududelivery.Customer.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fududelivery.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityUpdateAddAddressBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final TextInputEditText detailAddress;

  @NonNull
  public final TextInputEditText fullName;

  @NonNull
  public final TextInputEditText phoneNumber;

  @NonNull
  public final SwitchCompat setDefault;



  @NonNull
  public final TextView txtDetailAddress;

  @NonNull
  public final Button updateComplete;

  private ActivityUpdateAddAddressBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull TextInputEditText detailAddress,
      @NonNull TextInputEditText fullName, @NonNull TextInputEditText phoneNumber,
      @NonNull SwitchCompat setDefault,
      @NonNull TextView txtDetailAddress, @NonNull Button updateComplete) {
    this.rootView = rootView;

    this.detailAddress = detailAddress;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
    this.setDefault = setDefault;

    this.txtDetailAddress = txtDetailAddress;
    this.updateComplete = updateComplete;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityUpdateAddAddressBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityUpdateAddAddressBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_customer_addnewaddress, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityUpdateAddAddressBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {

      id = R.id.detail_address;
      TextInputEditText detailAddress = ViewBindings.findChildViewById(rootView, id);
      if (detailAddress == null) {
        break missingId;
      }

      id = R.id.ProfileName;
      TextInputEditText fullName = ViewBindings.findChildViewById(rootView, id);
      if (fullName == null) {
        break missingId;
      }

      id = R.id.ProfilePhone;
      TextInputEditText phoneNumber = ViewBindings.findChildViewById(rootView, id);
      if (phoneNumber == null) {
        break missingId;
      }

      id = R.id.set_default;
      SwitchCompat setDefault = ViewBindings.findChildViewById(rootView, id);
      if (setDefault == null) {
        break missingId;
      }


      id = R.id.txt_detail_address;
      TextView txtDetailAddress = ViewBindings.findChildViewById(rootView, id);
      if (txtDetailAddress == null) {
        break missingId;
      }

      id = R.id.save_button;
      Button updateComplete = ViewBindings.findChildViewById(rootView, id);
      if (updateComplete == null) {
        break missingId;
      }

      return new ActivityUpdateAddAddressBinding((LinearLayoutCompat) rootView,
          detailAddress, fullName, phoneNumber, setDefault, txtDetailAddress,
          updateComplete);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
