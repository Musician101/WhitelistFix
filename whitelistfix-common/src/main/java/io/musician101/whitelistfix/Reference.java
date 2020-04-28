package io.musician101.whitelistfix;

public class Reference {

    public static final String DESCRIPTION = "Fixes players not being kicked when the vanilla whitelist is activated/changed.";
    public static final String ID = "whitelist_fix";
    public static final String KICK_REASON = "You are not white-listed on this server!";
    public static final String NAME = "WhitelistFix";
    public static final String VERSION = "2.2";

    private Reference() {

    }

    public static class Commands {

        public static final String ON = "on";
        public static final String RELOAD = "reload";
        public static final String REMOVE = "remove";
        public static final String WHITELIST = "whitelist";

        private Commands() {

        }
    }
}
