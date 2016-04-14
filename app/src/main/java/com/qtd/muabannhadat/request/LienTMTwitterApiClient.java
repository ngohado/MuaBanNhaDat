package com.qtd.muabannhadat.request;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;

/**
 * Created by Dell on 4/14/2016.
 */
public class LienTMTwitterApiClient extends TwitterApiClient {
    public LienTMTwitterApiClient(Session session) {
        super(session);
    }
    public UsersService getUsersService() {
        return getService(UsersService.class);
    }
}
