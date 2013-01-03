package es.socialpoint.socialpointandroidlogin;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.UserSettingsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		callback = new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				onSessionStateChange(session, state, exception);
			}
		};
		uiHelper = new UiLifecycleHelper(this, callback);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
        userSettingsFragment = (UserSettingsFragment) fragmentManager.findFragmentById(R.id.login_fragment);
        userSettingsFragment.setSessionStatusCallback(new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                Log.d("LoginUsingLoginFragmentActivity", String.format("New session state: %s", state.toString()));
            }
        });
	}
	
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		isResumed = true;
		Log.d(TAG, "onResume()");
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
		isResumed = false;
		Log.d(TAG, "onPause()");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "onActivityResult()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
		Log.d(TAG, "onDestroy()");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState()");
	}
	
	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		Session session = Session.getActiveSession();

		if (session != null && session.isOpened()) {
			// if the session is already open,
			// try to show the selection fragment
			Log.d(TAG, "Sesion open.");
		} else {
			// otherwise present the splash screen
			// and ask the user to login.
			Log.d(TAG, "Sesion closed.");
		}
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		// TODO Auto-generated method stub
	}

	private UserSettingsFragment userSettingsFragment;
	
	private UiLifecycleHelper uiHelper;
	private boolean isResumed;
	private Session.StatusCallback callback;
	
	private static final String TAG = "MainActivity";
}
