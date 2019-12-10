const exec = require('shell-utils').exec;
const { includes, chain, split, filter } = require('lodash');

const fix = includes(process.argv, '--fix') ? '--fix' : '';

const dirs = [
  'lib/src',
  'integration',
  'e2e',
  'scripts',
  'playground/src'
];

run();

function run() {
  const paths = chain(dirs).map((d) => d === 'e2e' ? `${d}/**/*.[tj]s` : `${d}/**/*.[tj]sx?`).join(' ').value();
  exec.execSync(`tslint ${paths} ${fix} --format verbose`);
  assertAllTsFilesInSrc();
  exec.execSync(`jest --coverage`);
}

function assertAllTsFilesInSrc() {
  const allFiles = exec.execSyncRead('find ./lib/src -type f');
  const lines = split(allFiles, '\n');
  const offenders = filter(lines, (f) => !f.endsWith('.ts') && !f.endsWith('.tsx'));
  if (offenders.length) {
    throw new Error(`\n\nOnly ts/tsx files are allowed:\n${offenders.join('\n')}\n\n\n`);
  }
}
