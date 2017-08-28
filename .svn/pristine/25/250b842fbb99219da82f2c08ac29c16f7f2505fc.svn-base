package com.xiongyuan.lottery.mypage.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class AddManageDetails extends BaseActivity {
    @BindView(R.id.et_link)
    EditText etLink;
    @BindView(R.id.iv_qrcode)
    ImageView imageView;
    @BindView(R.id.tv_maxRebate)
    TextView tvMax;
    @BindView(R.id.tv_minRebate)
    TextView tvMin;
    @BindView(R.id.et_time)
    EditText etTime;
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_manage_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        etLink.setInputType(InputType.TYPE_NULL);
        etLink.setTextIsSelectable(true);
    }

    @Override
    public void initData() {
        etTime.setText(getIntent().getStringExtra("time"));
        tvMax.setText(getIntent().getStringExtra("max"));
        tvMin.setText(getIntent().getStringExtra("min"));
        etLink.setText(getIntent().getStringExtra("url"));
        generate(imageView,getIntent().getStringExtra("url"));
    }
    private void generate(ImageView iv,String url) {
        Bitmap qrBitmap = generateBitmap(url,600,600);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap bitmap = addLogo(qrBitmap, logoBitmap);
        iv.setImageBitmap(bitmap);
    }

    private Bitmap generateBitmap(String content,int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN,1);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }
}
