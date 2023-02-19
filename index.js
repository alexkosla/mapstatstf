function hideUpward() {
    $(document).ready(function(){
        $("#upward").click(function(){
          $("#upward").hide();
        });
      });
      console.log("hiding upward");
  }

function toggleHamburgerMenu(){
    console.log("activating hamburger menu");
    $('class-sidebar').animate({width: 'toggle'});
}

/* Set the width of the sidebar to 250px and the left margin of the page content to 250px */
function openNav() {
  document.getElementById("mySidebar").style.width = "220px";
  document.getElementById("main").style.marginLeft = "220px";
  // document.getElementById("indexmenu").className = "active";
  // document.getElementById("indexmenu").onclick = "closeNav()";
}

/* Set the width of the sidebar to 0 and the left margin of the page content to 0 */
function closeNav() {
  document.getElementById("mySidebar").style.width = "0";
  document.getElementById("main").style.marginLeft = "0";
  // document.getElementById("indexmenu").className = "";
  // document.getElementById("indexmenu").onclick = "openNav()";
} 