package cn.edu.jxnu.awesome_campus.ui.login;

import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.MainActivity;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.LibraryLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.SelfStudyRoomLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LibraryLoginFragment extends BaseLoginFragment{

    @Override
    protected void init() {
        super.init();
        setOnLineLayout(LibraryLoginUtil.isLogin());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里还要加点登录时的效果 progressbar 或是其他动画什么的
                setInputAreaEnable(false);
                LibraryLoginUtil.onLogin(usernameET,passwordET);
            }
        });

        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventModel<Void>(EVENT.JUMP_TO_LIBRARY_BORROWED));
            }
        });

        tips.setText("温馨提示:默认账号为本人校园卡卡号,密码与账号一致\n如遗忘密码,之前有绑定邮箱的则可直接通过邮箱找回，否则请联系图书馆工作人员");

    }

    @Override
    protected String getUsernameHint() {
        return this.getString(R.string.text_username);
    }

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.library);
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.LIBRARY__LOGIN_FAILURE_NULL_INPUT:
                setLoginFailureLayout();
                DisplayUtil.Snack(usernameET,InitApp.AppContext.getString(R.string.hint_null_input));
                break;
            case EVENT.LIBRARY_LOGIN_FAILURE:
                setLoginFailureLayout();
                DisplayUtil.Snack(usernameET,InitApp.AppContext.getString(R.string.hint_login_failed));
                break;
            case EVENT.LIBRARY_LOGIN_FAILURE_NETWORKERROR:
                setLoginFailureLayout();
                DisplayUtil.Snack(getView(),InitApp.AppContext.getString(R.string.hint_network_error));
                break;
            case EVENT.LIBRARY_LOGIN_SUCCESS:
                setOnLineLayout(true);
                setInputAreaEnable(true);
                MainActivity.presenter.updateHeader(getActivity());
                DisplayUtil.Snack(getView(),InitApp.AppContext.getString(R.string.hint_login_successful));
                break;

        }
    }

}
