//gets the element by its id
var myFile = document.getElementById('inputFile');

//binds to onchange event of the input field
myFile.addEventListener('change', function() {
    var child = document.getElementById('hb');
    var div;

    // Display a warning message if image if greater than 5MB
    if(this.files[0].size >= 5000000)
    {
        // Setting up div
        div = document.createElement('div');
        div.className = "alert alert-warning fade in";
        div.setAttribute("id", "warning-message");
        // setting up button
        var btn = document.createElement('button');
        btn.className = "close";
        btn.setAttribute("data-dismiss", "alert");
        btn.setAttribute("aria-label", "close");
        btn.innerHTML = '&times;';
        // append message
        div.appendChild(btn );
        div.appendChild(document.createTextNode('Image is too large to upload.') );
        // Insert after the reference node child
        child.parentNode.insertBefore(div, child.nextSibling);
    }
    else
    {
        var elem = document.getElementById("warning-message");
        elem.remove();
    }
});

