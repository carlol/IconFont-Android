package it.open.androidlab.util;


import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

/**
 * TODO: send email withot intent: http://www.tiemenschut.com/how-to-send-e-mail-directly-from-android-application/
 * @author c.luchessa
 *
 */
public class EmailUtils {

	/**
	 * 
	 * @param context
	 * @param emailAddress
	 * @param subject
	 * @param content
	 * @param isGmailPreferred
	 */
	@SuppressLint("DefaultLocale")
	public static void sendMail( Context context, String emailAddress
			, String subject, String content, boolean isGmailPreferred ) {
		
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO
				, Uri.fromParts("mailto", emailAddress, null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, content);

		if ( isGmailPreferred ) {
			PackageManager pm = context.getPackageManager();
			List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
			ResolveInfo gmail = null;
			for ( final ResolveInfo info : matches ) {
				if ( info.activityInfo.packageName.contains(".gm.") 		|| 
					 info.activityInfo.name.toLowerCase().contains("gmail") ){
					gmail = info;
				}
			}

			if ( gmail != null ) {
				emailIntent.setClassName(gmail.activityInfo.packageName, gmail.activityInfo.name);
			}
		}

		context.startActivity(Intent.createChooser(emailIntent, ""));
	}

}
