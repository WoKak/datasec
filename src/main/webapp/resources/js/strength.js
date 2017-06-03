function textareaLengthCheck() {

    var e = document.getElementsByClassName("strength");

    while(e[0])
        e[0].parentNode.removeChild(e[0]);

    var password = document.getElementById("password");
    var strength = document.createElement("tt");
    var length = password.value.length;
    var entropy = length * 6.569856;
    var node;

    if(entropy <= 30.0) {
        node = document.createTextNode("weak :(");
    }

    if(entropy > 30.0 && entropy <= 70.0) {
        node = document.createTextNode("medium :/");
    }

    if(entropy > 70.0) {
        node = document.createTextNode("strong :)");
    }


    strength.appendChild(node);

    strength.className="strength";

    insertAfter(strength, password);

}

function counter() {

    var textarea = document.getElementById("password");
    textarea.addEventListener("keyup", textareaLengthCheck, false);
    textareaLengthCheck();

}

function insertAfter(newNode, referenceNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}
