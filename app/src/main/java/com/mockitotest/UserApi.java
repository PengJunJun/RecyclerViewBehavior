package com.mockitotest;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by bykj003 on 2017/7/28.
 */

public interface UserApi {

    @GET("app/characters")
    Observable<ApiEntity<List<User>>> getUsers(@Query("currentPage") Integer currentPage,
                                               @Query("itemsPerPage") Integer itemPerPage);
}
