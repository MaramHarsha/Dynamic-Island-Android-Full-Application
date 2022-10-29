package com.nordan.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.textview.MaterialTextView;
import com.dynamic.island.harsha.notification.R;

import java.util.Optional;
import pl.droidsonroids.gif.GifImageView;

public class NordanAlertDialog {
    private NordanAlertDialog() {
    }

    public static class Builder {
        private Activity activity;
        private Animation animation;
        private int dialogAccentColor;
        private DialogType dialogType;
        private int headerColor;
        private Bitmap headerIconDrawable;
        private int headerIconResource;
        private boolean isCancelable;
        private boolean isGif;
        private String message;
        private String negativeBtnText;
        private NordanAlertDialogListener negativeListener;
        private String positiveBtnText;
        private NordanAlertDialogListener positiveListener;
        private String title;

        public Builder(Activity activity2) {
            this.activity = activity2;
        }

        public Builder setTitle(String str) {
            this.title = str;
            return this;
        }

        public Builder setDialogType(DialogType dialogType2) {
            this.dialogType = dialogType2;
            return this;
        }

        public Builder setDialogAccentColor(int i) {
            this.dialogAccentColor = i;
            return this;
        }

        public Builder setHeaderColor(int i) {
            this.headerColor = i;
            return this;
        }

        public Builder setMessage(String str) {
            this.message = str;
            return this;
        }

        public Builder setPositiveBtnText(String str) {
            this.positiveBtnText = str;
            return this;
        }

        public Builder setNegativeBtnText(String str) {
            this.negativeBtnText = str;
            return this;
        }

        public Builder setIcon(int i, boolean z) {
            this.headerIconResource = i;
            this.isGif = z;
            return this;
        }

        public Builder setIcon(Bitmap bitmap, boolean z) {
            this.headerIconDrawable = bitmap;
            this.isGif = z;
            return this;
        }

        public Builder setAnimation(Animation animation2) {
            this.animation = animation2;
            return this;
        }

        public Builder onPositiveClicked(NordanAlertDialogListener nordanAlertDialogListener) {
            this.positiveListener = nordanAlertDialogListener;
            return this;
        }

        public Builder onNegativeClicked(NordanAlertDialogListener nordanAlertDialogListener) {
            this.negativeListener = nordanAlertDialogListener;
            return this;
        }

        public Builder isCancellable(boolean z) {
            this.isCancelable = z;
            return this;
        }

        public Dialog build() {
            String str;
            String str2;
            Dialog animationDialog = getAnimationDialog();
            animationDialog.requestWindowFeature(1);
            Optional.ofNullable(animationDialog.getWindow()).ifPresent(new NordanAlertDialog$Builder$$ExternalSyntheticLambda0());
            animationDialog.setCancelable(this.isCancelable);
            animationDialog.setContentView(R.layout.nordan_alert_dialog);
            View findViewById = animationDialog.findViewById(R.id.background);
            GifImageView gifImageView = (GifImageView) animationDialog.findViewById(R.id.icon);
            Button materialButton = (Button) animationDialog.findViewById(R.id.negativeBtn);
            Button materialButton2 = (Button) animationDialog.findViewById(R.id.positiveBtn);
            ((MaterialTextView) animationDialog.findViewById(R.id.title)).setText(this.title);
            ((MaterialTextView) animationDialog.findViewById(R.id.message)).setText(this.message);
            if (this.dialogType != null) {
                int i = AnonymousClass1.$SwitchMap$com$nordan$dialog$DialogType[this.dialogType.ordinal()];
                if (i == 1) {
                    setErrorDialog(gifImageView, findViewById);
                } else if (i == 2) {
                    setWarningDialog(gifImageView, findViewById);
                } else if (i == 3) {
                    setQuestionDialog(gifImageView, findViewById);
                } else if (i == 4) {
                    setInformationDialog(gifImageView, findViewById);
                } else if (i == 5) {
                    setLevelCompleteDialog(gifImageView, findViewById);
                }
            } else {
                setCustomDialog(animationDialog, gifImageView, findViewById);
            }
            String str3 = this.positiveBtnText;
            if (str3 != null && !str3.isEmpty()) {
                materialButton2.setText(this.positiveBtnText);
            }
            String str4 = this.negativeBtnText;
            if (str4 == null || str4.isEmpty()) {
                materialButton.setVisibility(8);
            } else {
                materialButton.setText(this.negativeBtnText);
            }
            if (this.positiveListener == null || (str2 = this.positiveBtnText) == null || str2.isEmpty()) {
                materialButton2.setOnClickListener(new NordanAlertDialog$Builder$$ExternalSyntheticLambda2(animationDialog));
            } else {
                materialButton2.setOnClickListener(new NordanAlertDialog$Builder$$ExternalSyntheticLambda1(this, animationDialog));
            }
            if (this.negativeListener == null || (str = this.negativeBtnText) == null || str.isEmpty()) {
                materialButton.setOnClickListener(new NordanAlertDialog$Builder$$ExternalSyntheticLambda4(animationDialog));
            } else {
                materialButton.setVisibility(0);
                materialButton.setOnClickListener(new NordanAlertDialog$Builder$$ExternalSyntheticLambda3(this, animationDialog));
            }
            int i2 = this.dialogAccentColor;
            if (i2 > 0) {
                materialButton2.setBackgroundColor(this.activity.getColor(i2));
                materialButton.setTextColor(this.activity.getColor(this.dialogAccentColor));
            }
            return animationDialog;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$build$1$com-nordan-dialog-NordanAlertDialog$Builder  reason: not valid java name */
        public  void m116lambda$build$1$comnordandialogNordanAlertDialog$Builder(Dialog dialog, View view) {
            this.positiveListener.onClick();
            dialog.dismiss();
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$build$3$com-nordan-dialog-NordanAlertDialog$Builder  reason: not valid java name */
        public  void m117lambda$build$3$comnordandialogNordanAlertDialog$Builder(Dialog dialog, View view) {
            this.negativeListener.onClick();
            dialog.dismiss();
        }

        private void setLevelCompleteDialog(GifImageView gifImageView, View view) {
            gifImageView.setImageResource(R.drawable.ic_baseline_check_circle_24);
            gifImageView.setVisibility(0);
            view.setBackgroundColor(this.activity.getColor(R.color.colorGreen));
        }

        private void setWarningDialog(GifImageView gifImageView, View view) {
            gifImageView.setImageResource(R.drawable.ic_baseline_warning_24);
            gifImageView.setVisibility(0);
            view.setBackgroundColor(this.activity.getColor(R.color.colorYellow));
        }

        private void setErrorDialog(GifImageView gifImageView, View view) {
            gifImageView.setImageResource(R.drawable.ic_baseline_error_24);
            gifImageView.setVisibility(0);
            view.setBackgroundColor(this.activity.getColor(R.color.colorRed));
        }

        private void setQuestionDialog(GifImageView gifImageView, View view) {
            gifImageView.setImageResource(R.drawable.ic_baseline_help_24);
            gifImageView.setVisibility(0);
            view.setBackgroundColor(this.activity.getColor(R.color.colorBlue));
        }

        private void setInformationDialog(GifImageView gifImageView, View view) {
            gifImageView.setImageResource(R.drawable.ic_baseline_info_24);
            gifImageView.setVisibility(0);
            view.setBackgroundColor(this.activity.getColor(R.color.colorPurple));
        }

        private void setCustomDialog(Dialog dialog, GifImageView gifImageView, View view) {
            RelativeLayout relativeLayout = (RelativeLayout) dialog.findViewById(R.id.relative_header);
            int i = this.headerIconResource;
            if (i == 0 && this.headerColor == 0 && this.headerIconDrawable == null) {
                relativeLayout.setVisibility(8);
                return;
            }
            if (i != 0) {
                gifImageView.setImageResource(i);
                gifImageView.setVisibility(0);
                if (this.isGif) {
                    relativeLayout.getLayoutParams().height = 250;
                    gifImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
            int i2 = this.headerColor;
            if (i2 != 0) {
                view.setBackgroundColor(i2);
            }
        }

        private Dialog getAnimationDialog() {
            if (this.animation == null) {
                return new Dialog(this.activity);
            }
            int i = AnonymousClass1.$SwitchMap$com$nordan$dialog$Animation[this.animation.ordinal()];
            if (i == 1) {
                return new Dialog(this.activity, R.style.NordanDialogPopTheme);
            }
            if (i == 2) {
                return new Dialog(this.activity, R.style.NordanDialogSideTheme);
            }
            if (i != 3) {
                return new Dialog(this.activity);
            }
            return new Dialog(this.activity, R.style.NordanDialogSlideTheme);
        }
    }

    /* renamed from: com.nordan.dialog.NordanAlertDialog$1  reason: invalid class name */
    static  class AnonymousClass1 {
        static final  int[] $SwitchMap$com$nordan$dialog$Animation;
        static final  int[] $SwitchMap$com$nordan$dialog$DialogType;

        /* JADX WARNING: Can't wrap try/catch for region: R(17:0|(2:1|2)|3|(2:5|6)|7|9|10|11|13|14|15|16|17|18|19|20|(3:21|22|24)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|(2:5|6)|7|9|10|11|13|14|15|16|17|18|19|20|(3:21|22|24)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|1|2|3|(2:5|6)|7|9|10|11|13|14|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0039 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0043 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x004d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0058 */
        static {
            int[] iArr = new int[Animation.values().length];
            $SwitchMap$com$nordan$dialog$Animation = iArr;
            try {
                iArr[Animation.POP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$nordan$dialog$Animation[Animation.SIDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$nordan$dialog$Animation[Animation.SLIDE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[DialogType.values().length];
            $SwitchMap$com$nordan$dialog$DialogType = iArr2;
            iArr2[DialogType.ERROR.ordinal()] = 1;
            $SwitchMap$com$nordan$dialog$DialogType[DialogType.WARNING.ordinal()] = 2;
            $SwitchMap$com$nordan$dialog$DialogType[DialogType.QUESTION.ordinal()] = 3;
            $SwitchMap$com$nordan$dialog$DialogType[DialogType.INFORMATION.ordinal()] = 4;
            try {
                $SwitchMap$com$nordan$dialog$DialogType[DialogType.SUCCESS.ordinal()] = 5;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
