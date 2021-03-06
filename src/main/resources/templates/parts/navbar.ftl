<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/">WB</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item ">
                <a class="nav-link" href="/">Home</a>
            </li>
        <#if user??>
            <li class="nav-item ">
                <a class="nav-link" href="/main">Message</a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" href="/user-messages/${currentUserId}">My messages</a>
            </li>
        </#if>
        <#if isAdmin>
            <li class="nav-item ">
                <a class="nav-link" href="/user">User list</a>
            </li>
        </#if>
        <#if user??>
            <li class="nav-item ">
                <a class="nav-link" href="/user/profile">Profile</a>
            </li>
        </#if>
            <li class="nav-item ">
                <a class="nav-link"
                   href="/how_it_works">How it works</a>
            </li>
        </ul>
        <div class="navbar-text mr-3"><#if user??>${name}<#else >The user is not defined..</#if></div>
    <#if user??>
        <@l.logout/>
    <#else >
        <a class="btn btn-success" href="/login">Log in</a>
    </#if>
    </div>
</nav>