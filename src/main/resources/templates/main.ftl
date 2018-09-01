<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main</title>
</head>
<body>
<div>
    <form method="post">
        <input type="text" name="text" placeholder="Enter message...">
        <input type="text" name="tag" placeholder="Enter tag...">
        <#--<input type="hidden" name="_csrf" value="${_csrf.token}">-->
        <button type="submit">Add</button>
    </form>
</div>

<div>
    <form method="post" action="filter">
        <input type="text" name="filter" placeholder="Enter a tag to search for messages...">
        <#--<input type="hidden" name="_csrf" value="${_csrf.token}">-->
        <button type="submit">Find</button>
    </form>
</div>

<div>List messages</div>
<hr>
<#list messages as message>
<div>
    <b>${message.id}</b>
    <span>${message.text}</span>
    <i>${message.tag}</i>
</div>
</#list>
</body>
</html>