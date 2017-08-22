package com.wzf.boardgame.function.http.dto.response;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-03 10:03
 */

public class GameDetailResDto {
    /**
     * boardId : BI_1500700014801_0000100030
     * gameScore : 7.5
     * boardBackgroundUrl : http://os7i4k6w5.bkt.clouddn.com/image/cxw/1500699999096.png
     * boardImgUrl : http://os7i4k6w5.bkt.clouddn.com/image/cxw/1500699996348.png
     * boardDesigner : Sébastien Pauchon
     * isCollect : 0
     * gameTime : 30
     * boardTitle : 斋浦尔
     * boardEnglishTitle : Jaipur
     * boardAbstract : 玩家在游戏中扮演斋浦尔两个最强大的商人之一，为了争夺仅有的一个进见印度公王的机会，二人通过买卖、交换、发展骆驼商队等展开竞争，目的就是在游戏结束时拥有更多的钱财。
     * players : 2
     * difficultyDegree : 1.54
     * issueYear : 2009
     * issuePlace : 瑞士
     */

    private String boardId;
    private double gameScore;
    private String boardBackgroundUrl;
    private String boardImgUrl;
    private String boardDesigner;
    private int isCollect;
    private String gameTime;
    private String boardTitle;
    private String boardEnglishTitle;
    private String boardAbstract;
    private String players;
    private double difficultyDegree;
    private String issueYear;
    private String issuePlace;
    private int isVideo;
    private int isExtend;
    private int isRule;

    public int getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(int isVideo) {
        this.isVideo = isVideo;
    }

    public int getIsExtend() {
        return isExtend;
    }

    public void setIsExtend(int isExtend) {
        this.isExtend = isExtend;
    }

    public int getIsRule() {
        return isRule;
    }

    public void setIsRule(int isRule) {
        this.isRule = isRule;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public double getGameScore() {
        return gameScore;
    }

    public void setGameScore(double gameScore) {
        this.gameScore = gameScore;
    }

    public String getBoardBackgroundUrl() {
        return boardBackgroundUrl;
    }

    public void setBoardBackgroundUrl(String boardBackgroundUrl) {
        this.boardBackgroundUrl = boardBackgroundUrl;
    }

    public String getBoardImgUrl() {
        return boardImgUrl;
    }

    public void setBoardImgUrl(String boardImgUrl) {
        this.boardImgUrl = boardImgUrl;
    }

    public String getBoardDesigner() {
        return boardDesigner;
    }

    public void setBoardDesigner(String boardDesigner) {
        this.boardDesigner = boardDesigner;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardEnglishTitle() {
        return boardEnglishTitle;
    }

    public void setBoardEnglishTitle(String boardEnglishTitle) {
        this.boardEnglishTitle = boardEnglishTitle;
    }

    public String getBoardAbstract() {
        return boardAbstract;
    }

    public void setBoardAbstract(String boardAbstract) {
        this.boardAbstract = boardAbstract;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public double getDifficultyDegree() {
        return difficultyDegree;
    }

    public void setDifficultyDegree(double difficultyDegree) {
        this.difficultyDegree = difficultyDegree;
    }

    public String getIssueYear() {
        return issueYear;
    }

    public void setIssueYear(String issueYear) {
        this.issueYear = issueYear;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }
}
