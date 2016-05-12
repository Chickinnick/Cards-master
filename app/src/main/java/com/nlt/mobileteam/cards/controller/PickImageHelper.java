package com.nlt.mobileteam.cards.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

/**
 * Created by user on 12.05.2016.
 */
public class PickImageHelper {


    Activity activity;

    public PickImageHelper(Activity activity) {
        this.activity = activity;
    }

    protected void onPickFromDocumentsClicked() {
        /** Some devices such as Samsungs which have their own gallery app require write permission. Testing is advised! */

        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openDocuments(activity, 0);
        } else {
            Nammu.askForPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    EasyImage.openDocuments(activity, 0);
                }

                @Override
                public void permissionRefused() {

                }
            });
        }
    }

    protected void onPickFromGaleryClicked() {
        /** Some devices such as Samsungs which have their own gallery app require write permission. Testing is advised! */
        EasyImage.openGallery(activity, 0);
    }

    protected void onChooserClicked() {
        EasyImage.openChooserWithDocuments(activity, "Pick source", 0);
    }

    protected void onChooserWithGalleryClicked() {
        EasyImage.openChooserWithGallery(activity, "Pick source", 0);
    }

    protected void onActivityResult(final Activity activity, int requestCode, int resultCode, Intent data) {
        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                onPhotoReturned(imageFile);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(activity);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void onPhotoReturned(File photoFile) {
      /*  Picasso.with(this)
                .load(photoFile)
                .fit()
                .centerCrop()
                .into(imageView);*/
    }

}
