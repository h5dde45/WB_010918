<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
   aria-controls="collapseExample">
<#if !message??>
    Add new message
<#elseif message.id??>
    Message edit
<#else >
    Add new message
</#if >
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control
                       ${(textError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.text}</#if>"
                       name="text" placeholder="Enter message.."/>
            <#if textError??>
                <div class="invalid-feedback">
                ${textError}
                </div>
            </#if>
            </div>
            <div class="form-group">
                <input type="text" class="form-control
                        ${(tagError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.tag}</#if>"
                       name="tag" placeholder="Enter tag..">
            <#if tagError??>
                <div class="invalid-feedback">
                ${tagError}
                </div>
            </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Select the file in png format..</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save message</button>
            </div>
        </form>
    </div>
</div>