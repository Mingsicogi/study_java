<template>
  <div>
    <b-dropdown-item to="/game">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.game')">Game</span>
    </b-dropdown-item>
    <b-dropdown-item to="/game-server">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.gameServer')">Game Server</span>
    </b-dropdown-item>
    <b-dropdown-item to="/nid">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.nid')">Nid</span>
    </b-dropdown-item>
    <b-dropdown-item to="/gnid">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.gnid')">Gnid</span>
    </b-dropdown-item>
    <b-dropdown-item to="/spid">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.spid')">Spid</span>
    </b-dropdown-item>
    <b-dropdown-item to="/pid">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.pid')">Pid</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
