<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form" %>

<s:if test="%{#currentRoot.getChildren().length==0}">
    <s:set name="treeItemIconNameVar" value="'fa-folder-o'" />
</s:if>
<s:else>
    <s:set name="treeItemIconNameVar" value="#treeItemIconName" />
</s:else>

<tr id="<s:property value="#currentRoot.code" />" data-parent="#<s:property value="#currentRoot.parent.code" />" class="treeRow <s:if test="%{#currentRoot.code != 'homepage'}">collapsed childrenNodes</s:if>" >
        <td class="treegrid-node pointer">
         
        <input type="radio" name="<s:property value="#inputFieldName" />" id="fagianonode_<s:property value="#currentRoot.code" />" value="<s:property value="#currentRoot.code" />" 
              class="subTreeToggler hidden <s:if test="#currentRoot.children.length > 0">  tree_<s:property value="#currentRoot.code" /> </s:if>"
               <s:if test="#currentRoot.code == #selectedTreeNode"> checked="checked"</s:if> />
        &#32;<label for="fagianonode_<s:property value="#currentRoot.code" />"><span class="icon node-icon fa <s:property value="#treeItemIconNameVar" />"></span><s:property value="getTitle(#currentRoot.code, #currentRoot.titles)" /><s:if test="%{#currentRoot.group != null && !#currentRoot.group.equals('free')}">&#32;
                <span class="text-muted icon fa fa-lock"></span></s:if></label>
        </td>
</tr>

<s:if test="%{#currentRoot.getChildren().length>0}">
    <s:iterator value="#currentRoot.children" var="node">
        <s:set name="currentRoot" value="#node" />
        <s:include value="entryPage_treeBuilderPages.jsp" />
    </s:iterator>
</s:if>