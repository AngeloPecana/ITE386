let notes = [
    "This is my first note",
    "Remember to buy groceries",
    "Finish coding project by Friday"
];

const noteInput = document.getElementById('noteInput');
const noteForm = document.getElementById('noteForm');
const noteList = document.getElementById("noteList");

noteForm.addEventListener('submit', function(event) {
    event.preventDefault();
    
    const newNote = noteInput.value.trim();

    if (newNote !== "") {
        notes.push(newNote);
        noteInput.value = "";
        displayNotes();
    }
});

function displayNotes() {
    noteList.innerHTML = "";

    notes.forEach(function(note) {
        const noteItem = document.createElement("div");
        noteItem.classList.add("note");
        noteItem.textContent = note;
        noteList.appendChild(noteItem);
    });
}

displayNotes();
