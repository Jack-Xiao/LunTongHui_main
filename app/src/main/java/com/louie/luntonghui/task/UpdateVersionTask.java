package com.louie.luntonghui.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.louie.luntonghui.R;
import com.louie.luntonghui.util.ClientUtil;
import com.louie.luntonghui.util.Config;
import com.louie.luntonghui.util.DefaultShared;
import com.louie.luntonghui.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Administrator on 2015/7/16.
 */
public class UpdateVersionTask extends AsyncTask<Object, Integer, Boolean> {
    private Context mContext;
    private NumberProgressBar mProgressDialog;

    private int downLength = 0;
    private int total = 0;
    private int progress = 0;
    private int lastProgress = 0;
    private File newAPKFile;
    private MaterialDialog materialDialog;
    public static final int NEED_VIEW = 1;
    public static final int NOT_NEED_VIEW = 0;
    private int currViewType;

    public UpdateVersionTask(Context context, int viewType) {
        //mListener = context;
        mContext = context;
        //mProgressDialog = new ProgressDialog(mContext);
        currViewType = viewType;
        materialDialog = new MaterialDialog(mContext);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_progressbar, null);
        mProgressDialog = (NumberProgressBar) contentView.findViewById(R.id.number_progress_bar);
        materialDialog.setView(contentView)
                .setCanceledOnTouchOutside(false)
                .setTitle(R.string.updating);

        /*mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCanceledOnTouchOutside(false);*/

    }


    @Override
    protected void onPreExecute() {
        if (NEED_VIEW == currViewType) materialDialog.show();
        mProgressDialog.setProgress(progress);
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        boolean isSuccess = false;
        String downUrl = params[0].toString();
        try {
            URL url = new URL(downUrl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(Config.HTTP_CONNECT_TIMEOUT);
            http.setReadTimeout(Config.HTTP_READ_TIMEOUT);
            http.setRequestMethod("GET");
            http.connect(); //连接
            if (http.getResponseCode() == Config.CONNECT_SUCCESS) {
                total = http.getContentLength();
                newAPKFile = ClientUtil.createApkFile();
                InputStream inputStream = http.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); //缓存
                byte[] buffer = new byte[1024 * 10];
                while (true) {
                    int len = inputStream.read(buffer);
                    publishProgress(len);
                    if (len == -1) {
                        break; //读取完
                    }
                    outputStream.write(buffer, 0, len); //写入
                }
                outputStream.close();
                inputStream.close();
                byte[] data = outputStream.toByteArray();
                FileOutputStream fileOutputStream = new FileOutputStream(newAPKFile);
                fileOutputStream.write(data);
                fileOutputStream.flush();
                fileOutputStream.close();
                isSuccess = true;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            isSuccess = false;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        downLength += values[0];
        if (total != 0) {
            progress = (int) ((float) downLength / total * 100);
            if ((progress >= lastProgress + 1) || progress == 100) {
                lastProgress = progress;
                mProgressDialog.setProgress(progress);
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        UpdateService.finishUpdated();
        if (NEED_VIEW == currViewType) materialDialog.dismiss();
        if (isSuccess) {
            DefaultShared.putInt(UpdateService.UPDATE_SERVICE,
                    Config.NEED_UPDATE);
            try {
                Intent apkIntent = new Intent(Intent.ACTION_VIEW);
                apkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri puri = Uri.fromFile(newAPKFile);
                apkIntent.setDataAndType(puri, "application/vnd.android.package-archive");
                mContext.startActivity(apkIntent);
            } catch (Exception e) {
                ToastUtil.showShortToast(mContext, R.string.install_fail);
            }
        } else {
            ToastUtil.showShortToast(mContext, R.string.update_fail);
        }
    }
}
