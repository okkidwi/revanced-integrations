package app.revanced.integrations.patches.components;

import androidx.annotation.Nullable;

import app.revanced.integrations.settings.SettingsEnum;
import app.revanced.integrations.utils.StringTrieSearch;

public final class LayoutComponentsFilter extends Filter {
    private final StringTrieSearch exceptions = new StringTrieSearch();
    private final CustomFilterGroup custom;

    private final StringFilterGroup notifyMe;

    public LayoutComponentsFilter() {
        exceptions.addPatterns(
                "related_video_with_context",
                "comment_thread", // skip blocking anything in the comments
                "|comment.", // skip blocking anything in the comments replies
                "library_recent_shelf"
        );

        custom = new CustomFilterGroup(
                SettingsEnum.CUSTOM_FILTER,
                SettingsEnum.CUSTOM_FILTER_STRINGS
        );

        final var albumCard = new StringFilterGroup(
                SettingsEnum.HIDE_ALBUM_CARDS,
                "browsy_bar",
                "official_card"
        );

        final var audioTrackButton = new StringFilterGroup(
                SettingsEnum.HIDE_AUDIO_TRACK_BUTTON,
                "multi_feed_icon_button"
        );

        final var channelMemberShelf = new StringFilterGroup(
                SettingsEnum.HIDE_CHANNEL_MEMBER_SHELF,
                "member_recognition_shelf"
        );

        final var channelProfileLinks = new StringFilterGroup(
                SettingsEnum.HIDE_CHANNEL_PROFILE_LINKS,
                "channel_header_links"
        );

        final var chipsShelf = new StringFilterGroup(
                SettingsEnum.HIDE_CHIPS_SHELF,
                "chips_shelf"
        );

        final var graySeparator = new StringFilterGroup(
                SettingsEnum.HIDE_GRAY_SEPARATOR,
                "cell_divider"
        );

        final var infoPanel = new StringFilterGroup(
                SettingsEnum.HIDE_INFO_PANEL,
                "compact_banner",
                "publisher_transparency_panel",
                "single_item_information_panel"
        );

        final var joinMembership = new StringFilterGroup(
                SettingsEnum.HIDE_JOIN_BUTTON,
                "compact_sponsor_button"
        );

        final var latestPosts = new StringFilterGroup(
                SettingsEnum.HIDE_LATEST_POSTS,
                "post_shelf"
        );

        final var medicalPanel = new StringFilterGroup(
                SettingsEnum.HIDE_MEDICAL_PANEL,
                "emergency_onebox",
                "medical_panel"
        );

        final var movieShelf = new StringFilterGroup(
                SettingsEnum.HIDE_MOVIE_SHELF,
                "compact_movie",
                "horizontal_movie_shelf",
                "movie_and_show_upsell_card",
                "compact_tvfilm_item",
                "offer_module"
        );

        notifyMe = new StringFilterGroup(
                SettingsEnum.HIDE_NOTIFY_ME_BUTTON,
                "set_reminder_button"
        );

        final var searchBar = new StringFilterGroup(
                SettingsEnum.HIDE_SEARCH_BAR,
                "search_bar_entry_point"
        );

        final var startTrial = new StringFilterGroup(
                SettingsEnum.HIDE_START_TRIAL_BUTTON,
                "channel_purchase_button"
        );

        final var ticketShelf = new StringFilterGroup(
                SettingsEnum.HIDE_TICKET_SHELF,
                "ticket_horizontal_shelf",
                "ticket_shelf"
        );

        this.pathFilterGroupList.addAll(
                albumCard,
                audioTrackButton,
                channelMemberShelf,
                channelProfileLinks,
                custom,
                infoPanel,
                joinMembership,
                latestPosts,
                medicalPanel,
                movieShelf,
                notifyMe,
                startTrial,
                ticketShelf
        );

        this.identifierFilterGroupList.addAll(
                chipsShelf,
                graySeparator,
                searchBar
        );
    }

    @Override
    boolean isFiltered(String path, @Nullable String identifier, String allValue, byte[] protobufBufferArray,
                       FilterGroupList matchedList, FilterGroup matchedGroup, int matchedIndex) {
        // The groups are excluded from the filter due to the exceptions list below.
        // Filter them separately here.
        if (matchedGroup == notifyMe)
            return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedList, matchedGroup, matchedIndex);

        if (matchedGroup != custom && exceptions.matches(path))
            return false; // Exceptions are not filtered.

        return super.isFiltered(path, identifier, allValue, protobufBufferArray, matchedList, matchedGroup, matchedIndex);
    }
}