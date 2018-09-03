<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div>
    <@l.logout />
    <span><a href="/user">User list</a></span>
</div>
<div>
    <form method="post" action="/main" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Enter the message.." />
        <input type="text" name="tag" placeholder="Enter the tag..">
        <label> Select the PNG file: <input type="file" name="file" ></label>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Add</button>
    </form>
</div>
<div>Список сообщений</div>
<form method="get" action="/main">
    <input type="text" name="filter" value="${filter!}">
    <button type="submit">Find</button>
</form>
    <#list messages as message>
    <div>
        <b>${message.id}</b>
        <span>${message.text}</span>
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
        <div>
            <#if message.image??>
                <img src="/img/${message.id}" >
            </#if>
        </div>
    </div>
    <#else>
    No message
    </#list>
</@c.page>