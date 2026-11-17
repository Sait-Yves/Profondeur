const welcome = document.querySelector("#welcome");
const game = document.querySelector("#game");
const startForm = document.querySelector("#start-form");
const enemiesElement = document.querySelector("#enemies");
const handElement = document.querySelector("#hand");
const choicesElement = document.querySelector("#choices");
const journalElement = document.querySelector("#journal");
const toast = document.querySelector("#toast");
let gameState = null;
let selectedCard = null;
let selectedTarget = 0;
let toastTimer;

async function request(path, payload) {
  const options = payload === undefined
    ? { headers: { Accept: "application/json" } }
    : {
        method: "POST",
        headers: { "Content-Type": "application/json", Accept: "application/json" },
        body: JSON.stringify(payload)
      };
  const response = await fetch(path, options);
  const body = await response.json();
  if (!response.ok) throw new Error(body.error || "La requête n’a pas abouti.");
  return body;
}

function showError(message) {
  toast.textContent = message;
  toast.classList.add("visible");
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => toast.classList.remove("visible"), 3800);
}

function setText(selector, value) {
  document.querySelector(selector).textContent = value;
}

function render(state) {
  gameState = state;
  selectedTarget = 0;
  welcome.classList.toggle("hidden", !["title", "gameOver", "victory"].includes(state.phase));
  game.classList.toggle("hidden", state.phase === "title");
  document.querySelector("#new-game").classList.toggle("hidden", state.phase === "title");
  document.querySelector("#cancel-start").classList.add("hidden");
  if (state.phase === "title") return;
  if (state.phase === "gameOver" || state.phase === "victory") welcome.classList.remove("hidden");

  const phaseTitles = {
    combat: "Combat",
    reward: "Récompense",
    rest: "Feu de camp",
    event: "Rencontre",
    gameOver: "Fin de l’expédition",
    victory: "Victoire"
  };
  setText("#phase-title", phaseTitles[state.phase] || "Aventure");
  setText("#room-label", state.phase === "gameOver" ? "EXPÉDITION TERMINÉE" : "DONJON");
  setText("#room-counter", `Salle ${state.room} / ${state.roomCount}`);
  setText("#message", state.message || "");
  selectedCard = null;

  renderHero(state.hero);
  renderEnemies(state.enemies || [], state.phase === "combat");
  renderHand(state.hand || [], state.phase === "combat");
  renderChoices(state);
  renderJournal(state.journal || []);
}

function renderHero(hero) {
  if (!hero) return;
  setText("#hero-name-display", hero.name);
  setText("#health-value", `${hero.hp} / ${hero.maxHp}`);
  document.querySelector("#health-bar").style.width = `${hero.maxHp ? (hero.hp / hero.maxHp) * 100 : 0}%`;
  setText("#energy-value", hero.energy);
  setText("#coin-value", hero.coins);
  setText("#strength-value", hero.strength);
  setText("#block-value", hero.block);
  setText("#deck-size", hero.deckSize);
  setText("#discard-size", hero.discardSize);
  const effects = [];
  if (hero.weak > 0) effects.push(`Faiblesse ${hero.weak}`);
  if (hero.vulnerable > 0) effects.push(`Vulnérable ${hero.vulnerable}`);
  document.querySelector("#status-effects").replaceChildren(...effects.map((effect) => {
    const tag = document.createElement("span");
    tag.className = "status-tag";
    tag.textContent = effect;
    return tag;
  }));
}

function renderEnemies(enemies, selectable) {
  setText("#enemy-count", enemies.length ? `${enemies.length} ${enemies.length > 1 ? "ennemis" : "ennemi"}` : "");
  document.querySelector("#empty-arena").classList.toggle("hidden", enemies.length > 0);
  enemiesElement.replaceChildren(...enemies.map((enemy, index) => {
    const card = document.createElement("button");
    card.type = "button";
    card.className = "enemy-card" + (selectable ? " selectable" : "") + (selectedCard !== null && selectedTarget === index ? " is-target" : "");
    card.disabled = !selectable;
    card.setAttribute("aria-label", `${enemy.name}, ${enemy.hp} points de vie`);
    card.innerHTML = "";

    const art = document.createElement("span");
    art.className = "enemy-art";
    art.textContent = enemy.name.trim().charAt(0) || "×";
    const name = document.createElement("div");
    name.className = "enemy-name";
    name.textContent = enemy.name.trim();
    const hpRow = document.createElement("div");
    hpRow.className = "hp-row";
    const hpLabel = document.createElement("span");
    hpLabel.textContent = "PV";
    const hpValue = document.createElement("strong");
    hpValue.textContent = `${enemy.hp} / ${enemy.maxHp}`;
    hpRow.append(hpLabel, hpValue);
    const track = document.createElement("div");
    track.className = "hp-track";
    const bar = document.createElement("div");
    bar.className = "hp-bar";
    bar.style.width = `${enemy.maxHp ? (enemy.hp / enemy.maxHp) * 100 : 0}%`;
    track.append(bar);
    card.append(art, name, hpRow, track);
    card.addEventListener("click", () => {
      selectedTarget = index;
      renderEnemies(gameState.enemies, true);
      updatePlayButton();
    });
    return card;
  }));
}

function renderHand(cards, enabled) {
  document.querySelector("#action-panel").classList.toggle("hidden", !enabled);
  if (!enabled) return;
  setText("#turn-label", `— tour ${gameState.turn}`);
  handElement.replaceChildren(...cards.map((card, index) => {
    const button = document.createElement("button");
    button.type = "button";
    button.className = "card" + (selectedCard === index ? " selected" : "");
    button.disabled = !card.playable || card.cost > gameState.hero.energy;
    button.setAttribute("aria-pressed", selectedCard === index ? "true" : "false");
    const cost = document.createElement("span");
    cost.className = "card-cost";
    cost.textContent = card.cost;
    const title = document.createElement("span");
    title.className = "card-title";
    title.textContent = card.name;
    const type = document.createElement("span");
    type.className = "card-type";
    type.textContent = card.playable ? "CARTE DE COMBAT" : "INDISPONIBLE";
    button.append(cost, title, type);
    button.addEventListener("click", () => {
      selectedCard = index;
      renderHand(cards, true);
      updatePlayButton();
    });
    return button;
  }));
  document.querySelector("#end-turn").disabled = false;
  updatePlayButton();
}

function updatePlayButton() {
  const button = document.querySelector("#play-card");
  button.disabled = selectedCard === null || !gameState.enemies.length;
  setText("#selection-hint", selectedCard === null
    ? "Sélectionnez une carte, puis choisissez une cible."
    : `Cible sélectionnée : ${gameState.enemies[selectedTarget]?.name.trim() || "aucune"}.`);
}

function renderChoices(state) {
  const panel = document.querySelector("#choices-panel");
  const actionPanel = document.querySelector("#action-panel");
  panel.classList.toggle("hidden", !["reward", "rest", "event", "gameOver", "victory"].includes(state.phase));
  actionPanel.classList.toggle("hidden", state.phase !== "combat");
  choicesElement.replaceChildren();

  if (state.phase === "reward") {
    setText("#choices-eyebrow", "RÉCOMPENSE DE COMBAT");
    setText("#choices-title", "Choisissez une carte");
    state.rewards.forEach((card, index) => addChoice(`${card.name} · ${card.cost} énergie`, () => act("/api/reward", { index })));
  } else if (state.phase === "rest") {
    setText("#choices-eyebrow", "JUSQU’À DEUX ACTIONS");
    setText("#choices-title", `Que souhaitez-vous faire ? (${state.restChoices}/2)`);
    const options = [
      [2, "Dissiper les altérations · 10 pièces"],
      [3, "Récupérer 15 PV · 5 pièces"],
      [4, "Récupérer 45 PV · 7 pièces"],
      [5, "Récupérer 70 PV · 10 pièces"],
      [6, "Gagner 10 blocage · 10 pièces"],
      [7, "Gagner 5 force · 10 pièces"],
      [8, "Quitter le feu de camp"]
    ];
    options.forEach(([choice, label]) => {
      const costs = { 2: 10, 3: 5, 4: 7, 5: 10, 6: 10, 7: 10 };
      const disabled = choice !== 8 && (state.restChoices >= 2 || state.hero.coins < costs[choice]);
      addChoice(label, () => act("/api/rest", { choice }), disabled);
    });
    if (state.hero.coins >= 30 && state.restChoices < 2) {
      state.rewards.forEach((card, index) => {
        addChoice(`Acheter ${card.name} · 30 pièces`, () => act("/api/rest", { choice: 1, index }));
      });
    } else {
      addChoice("Ajouter une carte · 30 pièces", () => {}, true);
    }
  } else if (state.phase === "event") {
    setText("#choices-eyebrow", "RENCONTRE");
    setText("#choices-title", "Poursuivre votre route");
    addChoice("Continuer vers la salle suivante →", () => act("/api/continue", {}));
  } else if (state.phase === "gameOver" || state.phase === "victory") {
    setText("#choices-eyebrow", state.phase === "victory" ? "DONJON TERMINÉ" : "FIN DE PARTIE");
    setText("#choices-title", state.phase === "victory" ? "Félicitations, aventurier." : "Votre aventure s’achève ici.");
    addChoice("Lancer une nouvelle aventure", () => {
      startForm.scrollIntoView({ behavior: "smooth", block: "center" });
      document.querySelector("#hero-name").focus();
    });
  }
}

function addChoice(label, callback, disabled = false) {
  const button = document.createElement("button");
  button.type = "button";
  button.className = "choice-button";
  button.textContent = label;
  button.disabled = disabled;
  button.addEventListener("click", callback);
  choicesElement.append(button);
}

function renderJournal(entries) {
  journalElement.replaceChildren(...entries.map((entry) => {
    const item = document.createElement("li");
    item.textContent = entry;
    return item;
  }));
}

async function act(path, payload) {
  try {
    render(await request(path, payload));
  } catch (error) {
    showError(error.message);
  }
}

startForm.addEventListener("submit", async (event) => {
  event.preventDefault();
  const name = document.querySelector("#hero-name").value.trim() || "Aventurier";
  const difficulty = Number(document.querySelector("#difficulty").value);
  try {
    render(await request("/api/start", { name, difficulty }));
  } catch (error) {
    showError(error.message);
  }
});

document.querySelector("#new-game").addEventListener("click", () => {
  welcome.classList.remove("hidden");
  document.querySelector("#cancel-start").classList.remove("hidden");
  welcome.scrollIntoView({ behavior: "smooth", block: "center" });
});
document.querySelector("#cancel-start").addEventListener("click", () => {
  welcome.classList.add("hidden");
  document.querySelector("#cancel-start").classList.add("hidden");
});
document.querySelector("#play-card").addEventListener("click", () => {
  if (selectedCard === null) return;
  act("/api/play", { card: selectedCard, target: selectedTarget });
});
document.querySelector("#end-turn").addEventListener("click", () => act("/api/end-turn", {}));

request("/api/state").then(render).catch((error) => showError(error.message));
