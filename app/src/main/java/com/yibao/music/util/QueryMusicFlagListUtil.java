package com.yibao.music.util;

import com.yibao.music.model.MusicBean;
import com.yibao.music.model.greendao.MusicBeanDao;
import com.yibao.music.model.greendao.PlayListBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * @author Luoshipeng
 * @ Name:   QueryMusicFlagListUtil
 * @ Email:  strangermy98@gmail.com
 * @ Time:   2018/5/6/ 18:02
 * @ Des:    //根据条件查询MusicList
 */
public class QueryMusicFlagListUtil {

    /**
     * @param queryBuilder queryBuilder
     *                     //     * @param musicBean
     * @param pageFlag     页面标识:  1 按歌ABC、2 评分、3 播放次数、4 添加时间、5 自定义播放列表详情 、6 艺术家列表、7 专辑列表、
     *                     8 收藏列表、 10 搜索 按条件查询 like匹配
     * @param condition    查询关键字：艺术家、专辑、曲名 、播放列表
     * @return List
     */
    public static List<MusicBean> getMusicDataList(QueryBuilder<MusicBean> queryBuilder, int pageFlag, String condition) {

        if (pageFlag == Constant.NUMBER_ONE) {
            // 按歌ABC
            return MusicListUtil.sortMusicAbc(queryBuilder.list());
        } else if (pageFlag == Constant.NUMBER_TWO) {
            // 按评分
            return queryBuilder.orderDesc(MusicBeanDao.Properties.SongScore).build().list();
        } else if (pageFlag == Constant.NUMBER_THREE) {
            // 按播放次数
            return queryBuilder.orderDesc(MusicBeanDao.Properties.PlayFrequency).build().list();
        } else if (pageFlag == Constant.NUMBER_FOUR) {
            // 按添加时间
            return queryBuilder.orderDesc(MusicBeanDao.Properties.AddTime).build().list();
        } else if (pageFlag == Constant.NUMBER_FIVE) {
            // 自定义播放列表
            if (condition != null) {
                return MusicListUtil.sortMusicAbc(queryBuilder.where(MusicBeanDao.Properties.PlayListFlag.eq(condition)).build().list());

            } else {
                return MusicListUtil.sortMusicAbc(queryBuilder.build().list());
            }

        } else if (pageFlag == Constant.NUMBER_SIX) {
            // 艺术家列表数据
            if (condition != null) {
                return MusicListUtil.sortMusicAbc(queryBuilder.where(MusicBeanDao.Properties.Artist.eq(condition)).build().list());

            } else {
                return MusicListUtil.sortMusicAbc(queryBuilder.build().list());
            }

            // 专辑列表数据
        } else if (pageFlag == Constant.NUMBER_SEVEN) {

            if (condition != null) {
                return MusicListUtil.sortMusicAbc(queryBuilder.where(MusicBeanDao.Properties.Album.eq(condition)).build().list());
            } else {
                return MusicListUtil.sortMusicAbc(queryBuilder.build().list());
            }

            // 收藏列表
        } else if (pageFlag == Constant.NUMBER_EIGHT) {
            return queryBuilder.where(MusicBeanDao.Properties.IsFavorite.eq(true)).orderDesc(MusicBeanDao.Properties.Time).build().list();
        } else if (pageFlag == Constant.NUMBER_TEN) {
            // 10表示按条件查询
//            WhereCondition whereCondition = null;
//            // 按艺术家查询列表
//            if (dataFlag == Constant.NUMBER_ONE) {
//                whereCondition = MusicBeanDao.Properties.Artist.eq(queryFlag);
//                // 按专辑名查询列表
//            } else if (dataFlag == Constant.NUMBER_TWO) {
//                whereCondition = MusicBeanDao.Properties.Album.eq(queryFlag);
//                // 按歌曲名查询
//            } else if (dataFlag == Constant.NUMBER_THREE) {
//                whereCondition = MusicBeanDao.Properties.Title.eq(queryFlag);
//                // 按播放列表查询
//            } else if (dataFlag == Constant.NUMBER_FOUR) {
//                whereCondition = MusicBeanDao.Properties.PlayListFlag.eq(queryFlag);
//            }
//            if (whereCondition != null) {
//                return queryBuilder.where(whereCondition).build().list();
//            }
        }
        return MusicListUtil.sortMusicAbc(queryBuilder.build().list());
    }

    /**
     * getSpMusicFlag()先获取上次播放列表的标记，根据标记初始化对应的列表数据 。
     * <p>
     * 1 歌曲名   2  评分   3  播放次数        4  添加时间
     *
     * @return h
     */
    public static List<MusicBean> getDataList(int spMusicFlag, int dataFlag, String queryFlag, MusicBeanDao musicBeanDao) {
        if (spMusicFlag == Constant.NUMBER_THREE) {
            return MusicListUtil.sortMusicList(musicBeanDao.queryBuilder().list(), Constant.SORT_DOWN_TIME);
        } else if (spMusicFlag == Constant.NUMBER_ONE) {
            return MusicListUtil.sortMusicAbc(musicBeanDao.queryBuilder().list());
        } else if (spMusicFlag == Constant.NUMBER_EIGHT) {
            return musicBeanDao.queryBuilder().where(MusicBeanDao.Properties.IsFavorite.eq(true)).build().list();
        } else if (spMusicFlag == Constant.NUMBER_TEN) {
            WhereCondition whereCondition = null;
            // 按艺术家查询列表
            if (dataFlag == Constant.NUMBER_ONE) {
                whereCondition = MusicBeanDao.Properties.Artist.eq(queryFlag);
                // 按专辑名查询列表
            } else if (dataFlag == Constant.NUMBER_TWO) {
                whereCondition = MusicBeanDao.Properties.Album.eq(queryFlag);
                // 按歌曲名查询
            } else if (dataFlag == Constant.NUMBER_THREE) {
                whereCondition = MusicBeanDao.Properties.Title.eq(queryFlag);
                // 按播放列表查询
            } else if (dataFlag == Constant.NUMBER_FOUR) {
                whereCondition = MusicBeanDao.Properties.PlayListFlag.eq(queryFlag);
            }
            if (whereCondition != null) {
                return musicBeanDao.queryBuilder().where(whereCondition).build().list();
            }
        }
        return MusicListUtil.sortMusicAbc(musicBeanDao.queryBuilder().list());
    }

}
