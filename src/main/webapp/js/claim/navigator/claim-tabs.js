$(function () {

    var claimTabs = $('#claimTabs');

    claimTabs.find('a').click(function (e) {
        e.preventDefault();

        var url = $(this).attr("data-url");
        var href = this.hash;
        var pane = $(this);

        loadTab($(this), $(href), url)
    });

    function loadTab(activeTab, tabPanel, url) {

        tabPanel.load(url + "?objectReference=" + selectedNodeObjectReference, function (result) {
            activeTab.tab('show');
        });
    }

    function loadRoleTab() {
        var roleTabPanel = $('#roleTab');
        var activeTab = claimTabs.find('a.active');
        var url = activeTab.attr("data-url");


        loadTab(activeTab, roleTabPanel, url);
    }
    loadRoleTab();
});