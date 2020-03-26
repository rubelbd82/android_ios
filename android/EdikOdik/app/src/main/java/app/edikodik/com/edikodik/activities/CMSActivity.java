package app.edikodik.com.edikodik.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.entities.cms.CmsResponse;
import app.edikodik.com.edikodik.entities.cms.Title_content;
import app.edikodik.com.edikodik.entities.requests.CmsRequest;
import app.edikodik.com.edikodik.enums.ActivityType;
import app.edikodik.com.edikodik.enums.ServiceType;
import app.edikodik.com.edikodik.services.CmsService;
import app.edikodik.com.edikodik.utilities.CacheDb;

public class CMSActivity extends BaseActivity implements Handler.Callback {
    WebView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cms);

        super.initializeView(ActivityType.CMS);

//        Intent intent = getIntent();
//
//        String title = intent.getStringExtra("title");
//
//        if (title.isEmpty()) {
//            title = "Details";
//        }
//        getSupportActionBar().setTitle(title);

        this.initializeView();
    }

    private void initializeView() {
        Intent intent = getIntent();

        String cmsId = intent.getStringExtra("cmsId");

        content = (WebView) findViewById(R.id.content);

        CmsRequest request = new CmsRequest();
        request.setCmsId(cmsId);

        CmsService service = new CmsService(CMSActivity.this, this, true);

        service.execute(request);

    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.arg1 == ServiceType.CMS.ordinal()) {
            CmsResponse response = CacheDb.getCmsResponse();


            if (response.getTitle_content().length > 0) {
                Title_content cmsContent = response.getTitle_content()[0];
                Log.d("wid", "content: " + cmsContent.getContent());

                content.loadData(cmsContent.getContent(), "text/html", "UTF-8");
                getSupportActionBar().setTitle(cmsContent.getTitle());
            } else {
                content.loadData("Nothing found", "text/html", "UTF-8");
                getSupportActionBar().setTitle("404 Error");
            }
        }
        return false;
    }
}