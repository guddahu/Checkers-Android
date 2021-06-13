package edu.msu.cse476.cloudhatter.Cloud;

import edu.msu.cse476.cloudhatter.Cloud.Models.Catalog;
import edu.msu.cse476.cloudhatter.Cloud.Models.GameStart;
import edu.msu.cse476.cloudhatter.Cloud.Models.LoadResult;
import edu.msu.cse476.cloudhatter.Cloud.Models.PullResult;
import edu.msu.cse476.cloudhatter.Cloud.Models.PushResult;
import edu.msu.cse476.cloudhatter.Cloud.Models.SaveResult;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


import static edu.msu.cse476.cloudhatter.Cloud.Cloud.JOIN_PATH;
import static edu.msu.cse476.cloudhatter.Cloud.Cloud.LOAD_PATH;
import static edu.msu.cse476.cloudhatter.Cloud.Cloud.SAVE_PATH;
import static edu.msu.cse476.cloudhatter.Cloud.Cloud.GAME_START;
import static edu.msu.cse476.cloudhatter.Cloud.Cloud.PULL;
import static edu.msu.cse476.cloudhatter.Cloud.Cloud.PUSH;

public interface HatterService {

    @FormUrlEncoded
    @POST(SAVE_PATH)
    Call<SaveResult> saveHat(@Field("xml") String xmlData);

//    @GET(DELETE_PATH)
//    Call<Void> deleteHat(
//            @Query("user") String userId,
//            @Query("magic") String magic,
//            @Query("pw") String password,
//            @Query("id") String idHatToDelete
//    );
//
    @GET(LOAD_PATH)
    Call<LoadResult> loadHat(
            @Query("user") String userId,
            @Query("magic") String magic,
            @Query("pw") String password
            //@Query("id") String idHatToLoad
    );

    @GET(JOIN_PATH)
    Call<LoadResult> JoinPlayer(
            @Query("user") String userId,
            @Query("magic") String magic
            //@Query("id") String idHatToLoad
    );

    @GET(GAME_START)
    Call<GameStart> GameStart(
            @Query("user") String userId,
            @Query("magic") String magic
            //@Query("id") String idHatToLoad
    );

    @GET(PULL)
    Call<PullResult> PullResult(

    );

    @GET(PUSH)
    Call<PushResult> PushResult(
            @Query("user") String user,
            @Query("piece") String piece,
            @Query("king") String king,
            @Query("x") String x,
            @Query("y") String y

    );
}
